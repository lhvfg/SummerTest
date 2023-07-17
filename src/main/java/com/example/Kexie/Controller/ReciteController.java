package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.Util.DeriveWordUtil;
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
    DeriveWordUtil deriveWordUtil = new DeriveWordUtil();
    SynonymousUtil synonymousUtil = new SynonymousUtil();
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
        LambdaQueryWrapper<Word_user> lqw = new LambdaQueryWrapper<>();
        if(reciteDate.getRequestType().equals("getWords"))
        {
            List<Word> newWords = wordDao.selectNewWords(userId);
            List<Word> countOneWords = wordDao.selectCountWords(1,userId);
            List<Word> countTwoWords = wordDao.selectCountWords(2,userId);
//            List<Word_user> countOneWords = word_userDao.selectList(new LambdaQueryWrapper<Word_user>().eq(Word_user::getCount, 1).eq(Word_user::getUserId,userId));
//            List<Word_user> countTwoWords = word_userDao.selectList(new LambdaQueryWrapper<Word_user>().eq(Word_user::getCount, 2).eq(Word_user::getUserId,userId));
            getWordDate(result,newWords,userId,0);
            getWordDate(result,countOneWords,userId,1);
            getWordDate(result,countTwoWords,userId,2);
            result.setStatus("reciteWords");
        }
        else if (reciteDate.getRequestType().equals("right"))
        {
             if (word_userDao.countAdd(wordId,userId))
             {
                 result.setStatus("countAdd");
             }
        }
        else if(reciteDate.getRequestType().equals("wordRecite"))
        {
            if (word_userDao.wordRecite(wordId,userId))
            {
                result.setStatus("wordRecite");
            }
        }
        else if (reciteDate.getRequestType().equals("wrong"))
        {
            if (word_userDao.countClear(wordId,userId))
            {
                result.setStatus("countClear");
            }
        }
        else if(reciteDate.getRequestType().equals("reciteOver"))
        {
            if (userDao.changeNumTime(reciteDate.getNumber(),reciteDate.getTime(),userId))
            {
                result.setStatus("timeNumChanged");
            }
        }
        else if(reciteDate.getRequestType().equals("delete"))
        {
            if (word_userDao.delete(new LambdaQueryWrapper<Word_user>().eq(Word_user::getUserId,userId).eq(Word_user::getWordId,wordId))!=0)
            {
                result.setStatus("deleteSuccess");
            }
        }
        return result;
    }
    //整合单词显示数据
    private void getWordDate(Result result,List<Word> words,Integer userId,Integer count)
    {
        if (words.size()!=0) {
            for (int i = 0; i < words.size(); i++) {
                String spell = words.get(i).getSpell();
                reciteWordDates[count][i] = new ReciteWordDate();
                reciteWordDates[count][i].setSpell(spell);
                reciteWordDates[count][i].setCount(count);
                //例句
                List<Sentence> sentence = sentenceDao.selectList(new LambdaQueryWrapper<Sentence>().eq(Sentence::getWordId, words.get(i).getId()));
                reciteWordDates[count][i].setSentence(sentence);
                //笔记
                List<Note> notes = noteDao.selectList(new LambdaQueryWrapper<Note>().eq(Note::getWordId, words.get(i).getId()).eq(Note::getUserId, userId));
                reciteWordDates[count][i].setNotes(notes);
                //释义
                List<Meaning> meanings = meaningDao.selectList(new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId, words.get(i).getId()));
                if (!meanings.isEmpty()) {
                    reciteWordDates[count][i].setMeaning(meanings);
                    //近义词
                    int finalI = i;
                    meanings.forEach(meaning -> {
                        synonymousUtil.getSynonymous(reciteWordDates[count][finalI], meaning,meaningDao,wordDao);
                    });
                }
                //派生词
                reciteWordDates[count][i] = deriveWordUtil.getDerive(meaningDao,wordDao,reciteWordDates[count][i], spell);
            }
            if (count == 0)
                result.setReciteNewWordDates(reciteWordDates[count]);
            else if (count == 1)
                result.setReciteOneWordDates(reciteWordDates[count]);
            else if (count == 2)
                result.setRecitetwoWordDates(reciteWordDates[count]);
        }
    }
}


