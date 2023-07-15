package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.Result;
import org.junit.Test;
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
    ReciteWordDate[] reciteWordDates = new ReciteWordDate[10];
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
    @PostMapping("/recite")
    public Result Recitewords(@RequestBody ReciteFrontDate reciteDate)
    {
        Integer userId = reciteDate.getUserId();
        LambdaQueryWrapper<Word_user> lqw = new LambdaQueryWrapper<>();
        if(reciteDate.getRequestType().equals("getWords"))
        {
            List<Word> newWords = wordDao.selectNewWords(userId);
            List<Word_user> countOneWords = word_userDao.selectList(new LambdaQueryWrapper<Word_user>().eq(Word_user::getCount, 1).eq(Word_user::getUserId,userId));
            List<Word_user> countTwoWords = word_userDao.selectList(new LambdaQueryWrapper<Word_user>().eq(Word_user::getCount, 2).eq(Word_user::getUserId,userId));
            getWordDate(result,newWords,userId);
        }
        return result;
    }
    //整合单词显示数据
    private void getWordDate(Result result,List<Word> words,Integer userId)
    {
        for (int i = 0; i < words.size(); i++) {
            reciteWordDates[i] = new ReciteWordDate();
            reciteWordDates[i].setSpell(words.get(i).getSpell());
            reciteWordDates[i].setCount(0);
            //例句
            Sentence sentence= sentenceDao.selectOne(new LambdaQueryWrapper<Sentence>().eq(Sentence::getWordId,words.get(i).getId()));
            reciteWordDates[i].setSentence(sentence);
            //笔记
            List<Note> notes= noteDao.selectList(new LambdaQueryWrapper<Note>().eq(Note::getWordId,words.get(i).getId()).eq(Note::getUserId,userId));
            reciteWordDates[i].setNotes(notes);
            //释义
            Meaning meaning= meaningDao.selectOne(new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId,words.get(i).getId()));
            reciteWordDates[i].setMeaning(meaning);
            //近义词
            getSynonymous(reciteWordDates[i],meaning);
            //派生词
            System.out.println(reciteWordDates[i]);
        }
        result.setReciteWordDates(reciteWordDates);
    }
    //获取近义词
    private void getSynonymous(ReciteWordDate reciteWordDate,Meaning wordMean){
        Map<Map<String,String>,Set<String>> Synonymous = new HashMap<>();
        Set<String> spells = new TreeSet<>();
        Map<String,String> mean = new HashMap<>();
        String[] specificChar = {",", "，",";","；"};
        boolean f = false;
        //用中英文逗号和分号符号分割字符串
        for (int i = 0; i < specificChar.length; i++) {
            if (wordMean.getContent().contains(specificChar[i])) {
                f=true;
                for (String wordMeanContent : wordMean.getContent().split(specificChar[i])) {
                    //获取含有相同释义的所有meaning数据
                    List<Meaning> meanings = meaningDao.selectList(
                            new LambdaQueryWrapper<Meaning>()
                                    .eq(Meaning::getFunction, wordMean.getFunction())//词性相同
                                    .like(Meaning::getContent, wordMeanContent)//模糊查询，包含相同释义
                                    .ne(Meaning::getId, wordMean.getId())//不是自己
                    );
                    //如果存在除了自己的近义词
                    if (meanings.size() > 0) {
                        System.out.println("存在近义词");
                        //设置前键
                        mean.put(meanings.get(0).getFunction(), wordMeanContent);
                        //对于每个释义相应的单词id，查询对应拼写
                        meanings.forEach(m -> {
                            String spell = wordDao.selectReciteWordSpell(m.getWordId());
                            spells.add(spell);
                        });
                        Synonymous.put(mean, spells);
                        //返回除了自己所有近义词的拼写
                        reciteWordDate.setSynonymous(Synonymous);
                        System.out.println(reciteWordDate);
                    }
                }
            }
        }
        if (!f)
        {
            //获取含有相同释义的所有meaning数据
            List<Meaning> meanings = meaningDao.selectList(
                    new LambdaQueryWrapper<Meaning>()
                                .eq(Meaning::getFunction, wordMean.getFunction())//词性相同
                                .like(Meaning::getContent, wordMean.getContent())//模糊查询，包含相同释义
                                .ne(Meaning::getId, wordMean.getId()));   //不是自己
            //如果存在除了自己的近义词
            if (meanings.size() > 0) {
                System.out.println("存在近义词");
                //设置前键
                mean.put(meanings.get(0).getFunction(), wordMean.getContent());
                //对于每个释义相应的单词id，查询对应拼写
                meanings.forEach(m -> {
                    String spell = wordDao.selectReciteWordSpell(m.getWordId());
                    spells.add(spell);
                });
                Synonymous.put(mean, spells);
                //返回除了自己所有近义词的拼写
                reciteWordDate.setSynonymous(Synonymous);
                System.out.println(reciteWordDate);
                }
        }
    }
    //    //获取派生词
//    private void getDerive(Word word){
//
//    }
}


