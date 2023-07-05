package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Kexie.dao.MeaningDao;
import com.example.Kexie.dao.NoteDao;
import com.example.Kexie.dao.SentenceDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class WordController {
    @Autowired
    WordDao wordDao;
    @Autowired
    NoteDao noteDao;
    @Autowired
    SentenceDao sentenceDao;
    @Autowired
    MeaningDao meaningDao;
    @PostMapping("/addword")
    public Result addWordRequest(@RequestBody WordDate wordDate){
        Result result = new Result();
        QueryWrapper<Word> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Word> lqw = new LambdaQueryWrapper<>();
        // 判断单词是否存在
        if(wordDate.getRequestType().equals("addWordRequest"))
        {
            lqw.eq(Word::getSpell, wordDate.getSpell());
            Word word = wordDao.selectOne(lqw);
            if (word != null)
            {
                result.setStatus("wordExisted");
                result.setWordId(word.getId());
            }
            else{
                qw.select("max(id) as id");
                result.setStatus("wordNotExist");
                result.setWordId(wordDao.selectOne(qw).getId()+1);
                System.out.println(wordDao.selectOne(qw).getId());
            }
        }
        //添加单词
        else if(wordDate.getRequestType().equals("addWord"))
        {
            int noteNum= wordDate.getNoteNum();
            int meaningNum= wordDate.getMeaningNum();
            int sentenceNum= wordDate.getSentenceNum();
            int wordId = wordDate.getWordId();
            String[] noteContent = wordDate.getNoteContent();
            String[] sentenceContent = wordDate.getSentenceContent();
            String[] sentenceContentMean = wordDate.getSentenceContentMean();
            String[] meaningContent = wordDate.getMeaningContent();
            String[] function = wordDate.getFunction();
            Word word = new Word(wordId,wordDate.getSpell(),noteNum);
            if(wordDate.getWordType()==1)
            {
                wordDao.insert(word);
            }
            else {
                wordDao.updateById(word);
            }
            for(int i=0;i<noteNum;i++)
            {
                Note note = new Note();
                note.setWordId(wordId);
                note.setContent(noteContent[i]);
                noteDao.insert(note);
            }
            for(int i=0;i<sentenceNum;i++)
            {
                Sentence sentence = new Sentence();
                sentence.setWordId(wordId);
                sentence.setContent(sentenceContent[i]);
                sentence.setContentMean(sentenceContentMean[i]);
                sentenceDao.insert(sentence);
            }
            for(int i=0;i<meaningNum;i++)
            {
                Meaning meaning = new Meaning();
                meaning.setWordId(wordId);
                meaning.setContent(meaningContent[i]);
                meaning.setFunction(function[i]);
                meaningDao.insert(meaning);
            }
            result.setStatus("addWordSucceed");
        }
        //获取单词书列表
        else if (wordDate.getWordType().equals(""))
        return result;
    };

}
