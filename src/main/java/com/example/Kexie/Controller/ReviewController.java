package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.Util.DeriveWordUtil;
import com.example.Kexie.Util.GetNumUtil;
import com.example.Kexie.Util.SynonymousUtil;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.FrontData.ReviewFrontData;
import com.example.Kexie.domain.Result.ReciteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class ReviewController {
    @Autowired
    UserDao userDao;
    @Autowired
    Book_userDao book_userDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    Book_wordDao book_wordDao;
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
    DeriveWordUtil deriveWordUtil = new DeriveWordUtil();
    SynonymousUtil synonymousUtil = new SynonymousUtil();
    ArrayList<ReciteWordData> reciteWordData = new ArrayList<>();
    @PostMapping("/review")
    public ReciteResult review (@RequestBody ReviewFrontData reviewDate)
    {
        ReciteResult result = new ReciteResult();
        Integer bookId = reviewDate.getBookId();
        Integer userId = reviewDate.getUserId();
        Integer wordId = reviewDate.getWordId();
        String today = reviewDate.getToday();
        //初始化单词数据
        reciteWordData = new ArrayList<>();
        if (reviewDate.getRequestType().equals("getNum"))
        {
            GetNumUtil getNumUtil = new GetNumUtil();
            result.setWordNum(getNumUtil.getNum("reviewNum",today,bookId,userId,wordDao,null,null));
            result.setStatus("reciteNumSuccess");
        }
        else if(reviewDate.getRequestType().equals("getWords"))
        {
            List<Word> bookWords = wordDao.selectReviewBookWords(userId,bookId,today);
            List<Word> starWords = wordDao.selectReviewStarWords(userId,today);
            getWordDate(starWords,userId,0,starWords.size(),true);
            getWordDate(bookWords,userId,starWords.size(),Math.min(10,starWords.size()+bookWords.size()),false);
            result.setStatus("getWordSuccess");
            result.setReviewWords(reciteWordData);
        }
        //设置stage和下次复习时间
        else if (reviewDate.getRequestType().equals("setNextTime"))
        {
            Date date =Date.valueOf(reviewDate.getNextTime());
            System.out.println(reviewDate.getNextTime());
            Word_user word_user = new Word_user(date,reviewDate.getStage());
            if (word_userDao.update(word_user,new LambdaQueryWrapper<Word_user>().eq(Word_user::getWordId,wordId).eq(Word_user::getUserId,userId))==1) {
                result.setStatus("setTimeSuccess");
            }
        }
        return result;
    };
    //整合单词显示数据
    private void getWordDate(List<Word> words, Integer userId, Integer beg, Integer end, boolean star)
    {
        if (words.size()!=0) {
            System.out.println(words.size());
            for (int i = beg; i < end; i++) {
                String spell = words.get(i-beg).getSpell();
                Integer wordId = words.get(i-beg).getId();
                System.out.println("添加单词"+spell);
                reciteWordData.add(new ReciteWordData());
                    System.out.println("添加数据后recite长度为"+reciteWordData.size());
                    reciteWordData.get(i).setSpell(spell);
                    //此处count为所处答题阶段 -1未复习过、0忘记了，1模糊，2完成
                    reciteWordData.get(i).setCount(-1);
                    reciteWordData.get(i).setStar(star);
                    reciteWordData.get(i).setWordId(wordId);
                    //stage为遗忘曲线间隔的阶段，0下次天数加2，1加3,2加5,3加8,4加16
                    reciteWordData.get(i).setStage(word_userDao.selectStage(wordId,userId));
                    //例句
                    List<Sentence> sentence = sentenceDao.selectList(new LambdaQueryWrapper<Sentence>().eq(Sentence::getWordId, wordId));
                    reciteWordData.get(i).setSentence(sentence);
                    //笔记
                    List<Note> notes = noteDao.selectList(new LambdaQueryWrapper<Note>().eq(Note::getWordId, wordId).eq(Note::getUserId, userId));
                    reciteWordData.get(i).setNotes(notes);
                    //释义
                    List<Meaning> meanings = meaningDao.selectList(new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId, wordId));
                    if (!meanings.isEmpty()) {
                        reciteWordData.get(i).setMeaning(meanings);
                        //近义词
                        int finalI = i;
                        meanings.forEach(meaning -> {
                            synonymousUtil.getSynonymous(reciteWordData.get(finalI), meaning, meaningDao, wordDao);
                        });
                    }
                    //派生词
                    reciteWordData.set(i,deriveWordUtil.getDerive(meaningDao, wordDao, reciteWordData.get(i), spell));
                    System.out.println("此时i的值为"+i);
                }
            }
            System.out.println(star+"生词结束后reciteWordDate是"+reciteWordData);
        }
}


