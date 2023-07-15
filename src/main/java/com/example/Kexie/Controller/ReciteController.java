package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class ReciteController {
    Result result = new Result();
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
        ReciteWordDate[] reciteWordDates = new ReciteWordDate[10];
        for (int i = 0; i < words.size(); i++) {
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
            ArrayList<String> Synonymous = new ArrayList<>();
            List<Meaning> meanings = meaningDao.selectList(new LambdaQueryWrapper<Meaning>().eq(Meaning::getFunction, meaning.getFunction()).eq(Meaning::getContent, meaning.getContent()));
            meanings.forEach(m->{
                String spell = wordDao.selectOne(new LambdaQueryWrapper<Word>().eq(Word::getId, m.getWordId())).getSpell();
                if(spell!=null)
                {
                    Synonymous.add(spell);
                }
            });
            reciteWordDates[i].setSynonymous(Synonymous);
            //派生词

        }
        result.setReciteWordDates(reciteWordDates);
    }
    //获取派生词
    private ArrayList<String> getDerive(Word word){

    }
}
