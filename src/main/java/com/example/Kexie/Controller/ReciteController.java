package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.Util.DeriveWordUtil;
import com.example.Kexie.Util.GetWordDateUtil;
import com.example.Kexie.Util.SynonymousUtil;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Transactional
public class ReciteController {
    Result result = new Result();
    ReciteWordDate[][] reciteWordDates = new ReciteWordDate[3][10];
    ArrayList<Set<String>> wordSet = new ArrayList<>();
    DeriveWordUtil deriveWordUtil = new DeriveWordUtil();
    SynonymousUtil synonymousUtil = new SynonymousUtil();
//  GetWordDateUtil getWordDateUtil = new GetWordDateUtil();
    @Autowired
    Book_userDao book_userDao;
    @Autowired
    Word_userDao word_userDao;
    @Autowired
    WordDao wordDao;
    @Autowired
    SentenceDao sentenceDao;
    @Autowired
    NoteDao noteDao;
    @Autowired
    MeaningDao meaningDao;
    @Autowired
    UserDao userDao;
    @PostMapping("/recite")
    public Result Recitewords(@RequestBody ReciteFrontDate reciteDate)
    {
        Integer userId = reciteDate.getUserId();
        Integer wordId = reciteDate.getWordId();
        Integer bookId = reciteDate.getBookId();
        LambdaQueryWrapper<Word_user> lqw = new LambdaQueryWrapper<>();
        //进入页面
        if(reciteDate.getRequestType().equals("getWords"))
        {
            List<Word> newWords = wordDao.selectNewWords(bookId);
            List<Word> newStarWords = wordDao.selectNewStarWords(userId);
            List<Word> countOneWords = wordDao.selectCountWords(1,userId,bookId);
            List<Word> countOneStarWords = wordDao.selectCountStarWords(1,userId);
            List<Word> countTwoWords = wordDao.selectCountWords(2,userId,bookId);
            List<Word> countTwoStarWords = wordDao.selectCountStarWords(2,userId);
            //初始化
            for (int i = 0; i < 3; i++) {
                wordSet.add(new HashSet<>());
            }
            // 优先获取生词本中的单词
            getWordDate(newStarWords, userId, 0, 0, newStarWords.size());
            getWordDate(newWords, userId, 0, newStarWords.size(), Math.min(10,newWords.size()+newStarWords.size()));
            // 优先获取生词本中的单词
            getWordDate(countOneStarWords, userId, 1, 0, countOneStarWords.size());
            getWordDate(countOneWords, userId, 1, countOneStarWords.size(), Math.min(10,countOneStarWords.size()+countOneWords.size()));
            // 优先获取生词本中的单词
            getWordDate(countTwoStarWords, userId, 2, 0, countTwoStarWords.size());
            getWordDate(countTwoWords, userId, 2, countTwoStarWords.size(), Math.min(10,countTwoStarWords.size()+countTwoWords.size()));
            result = new Result("reciteWords",reciteWordDates[0],reciteWordDates[1],reciteWordDates[2]);
        }
        //答对一次
        else if (reciteDate.getRequestType().equals("right"))
        {
             if (word_userDao.countAdd(wordId,userId))
             {
                 result.setStatus("countAdd");
             }
        }
        //完成背诵
        else if(reciteDate.getRequestType().equals("wordRecite"))
        {
            if (word_userDao.wordRecite(wordId,userId))
            {
                result.setStatus("wordRecite");
            }
        }
        //答错
        else if (reciteDate.getRequestType().equals("wrong"))
        {
            if (word_userDao.countClear(wordId,userId))
            {
                result.setStatus("countClear");
            }
        }
        //离开背单词界面
        else if(reciteDate.getRequestType().equals("reciteOver"))
        {
            if (userDao.changeNumTime(reciteDate.getNumber(),reciteDate.getTime(),userId))
            {
                result.setStatus("timeNumChanged");
            }
        }
        //标熟
        else if(reciteDate.getRequestType().equals("delete"))
        {
            if (word_userDao.delete(new LambdaQueryWrapper<Word_user>().eq(Word_user::getUserId,userId).eq(Word_user::getWordId,wordId))!=0)
            {
                result.setStatus("deleteSuccess");
            }
        }
        //加入生词本
        return result;
    }

    //整合单词显示数据
    private void getWordDate(List<Word> words,Integer userId,Integer count,Integer beg,Integer end)
    {
        if (words.size()!=0) {
            for (int i = beg; i < end; i++) {
                String spell = words.get(i-beg).getSpell();
                Integer wordId = words.get(i-beg).getId();
                if (wordSet.get(count).isEmpty()||(!wordSet.get(count).isEmpty()&&!wordSet.get(count).contains(spell)))
                {
                    wordSet.get(count).add(spell);
                    reciteWordDates[count][i] = new ReciteWordDate();
                    reciteWordDates[count][i].setSpell(spell);
                    reciteWordDates[count][i].setCount(count);
                    //例句
                    List<Sentence> sentence = sentenceDao.selectList(new LambdaQueryWrapper<Sentence>().eq(Sentence::getWordId, wordId));
                    reciteWordDates[count][i].setSentence(sentence);
                    //笔记
                    List<Note> notes = noteDao.selectList(new LambdaQueryWrapper<Note>().eq(Note::getWordId, wordId).eq(Note::getUserId, userId));
                    reciteWordDates[count][i].setNotes(notes);
                    //释义
                    List<Meaning> meanings = meaningDao.selectList(new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId, wordId));
                    if (!meanings.isEmpty()) {
                        reciteWordDates[count][i].setMeaning(meanings);
                        //近义词
                        int finalI = i;
                        meanings.forEach(meaning -> {
                            synonymousUtil.getSynonymous(reciteWordDates[count][finalI], meaning, meaningDao, wordDao);
                        });
                    }
                    //派生词
                    reciteWordDates[count][i] = deriveWordUtil.getDerive(meaningDao, wordDao, reciteWordDates[count][i], spell);
                }
            }
        }
    }
}


