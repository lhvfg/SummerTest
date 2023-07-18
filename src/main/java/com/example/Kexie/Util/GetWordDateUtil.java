package com.example.Kexie.Util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.dao.MeaningDao;
import com.example.Kexie.dao.NoteDao;
import com.example.Kexie.dao.SentenceDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.BasicPojo.Meaning;
import com.example.Kexie.domain.BasicPojo.Note;
import com.example.Kexie.domain.BasicPojo.Sentence;
import com.example.Kexie.domain.BasicPojo.Word;
import com.example.Kexie.domain.ReciteWordDate;
import com.example.Kexie.domain.Result;

import java.util.List;

public class GetWordDateUtil {
    SynonymousUtil synonymousUtil = new SynonymousUtil();
    DeriveWordUtil deriveWordUtil = new DeriveWordUtil();
        //整合单词显示数据
        public Result getWordDate(Result result, List< Word > words, Integer userId, Integer count, Integer beg, Integer end, ReciteWordDate[][] reciteWordDates, SentenceDao sentenceDao, NoteDao noteDao, MeaningDao meaningDao, WordDao wordDao)
        {
            if (words.size()!=0) {
                for (int i = beg; i < end; i++) {
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
            return result;
        }
    }
