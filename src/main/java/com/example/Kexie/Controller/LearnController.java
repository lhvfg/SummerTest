package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.Util.DeriveWordUtil;
import com.example.Kexie.Util.GetNumUtil;
import com.example.Kexie.Util.SynonymousUtil;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.FrontData.ReciteFrontData;
import com.example.Kexie.domain.Result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Transactional
public class LearnController {
    Result result = new Result();
    //不定长二维数组
    ArrayList<ArrayList<ReciteWordData>> reciteWordDates = new ArrayList<>();
    ArrayList<Set<String>> wordSet = new ArrayList<>();
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
    @Autowired
    StarBookDao starBookDao;
    @Autowired
    Book_wordDao book_wordDao;
    @PostMapping("/recite")
    public Result Recitewords(@RequestBody ReciteFrontData reciteDate)
    {
        Integer userId = reciteDate.getUserId();
        Integer wordId = reciteDate.getWordId();
        Integer bookId = reciteDate.getBookId();
        LambdaQueryWrapper<Word_user> lqw = new LambdaQueryWrapper<>();
        //进入页面
        if(reciteDate.getRequestType().equals("getWords"))
        {
            wordSet = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ArrayList<ReciteWordData> arrayList = new ArrayList<>();
                if (reciteWordDates.size()==i)
                    reciteWordDates.add(arrayList);
                else
                    reciteWordDates.set(i,arrayList);
            }
            List<Word> newWords = wordDao.selectNewWords(bookId,userId);
            System.out.println(newWords);
            List<Word> newStarWords = wordDao.selectNewStarWords(userId,bookId);
            System.out.println(newStarWords);
            List<Word> countOneWords = wordDao.selectCountWords(1,userId,bookId);
            List<Word> countOneStarWords = wordDao.selectCountStarWords(1,userId);
            List<Word> countTwoWords = wordDao.selectCountWords(2,userId,bookId);
            List<Word> countTwoStarWords = wordDao.selectCountStarWords(2,userId);
            //初始化
            for (int i = 0; i < 3; i++) {
                wordSet.add(new HashSet<>());
            }
            // 优先获取生词本中的单词
            getWordDate(newStarWords, userId, 0, 0, newStarWords.size(),true);
            getWordDate(newWords, userId, 0, newStarWords.size(), Math.min(10,newWords.size()+newStarWords.size()),false);
            // 优先获取生词本中的单词
            getWordDate(countOneStarWords, userId, 1, 0, countOneStarWords.size(),true);
            getWordDate(countOneWords, userId, 1, countOneStarWords.size(), Math.min(10,countOneStarWords.size()+countOneWords.size()),false);
            // 优先获取生词本中的单词
            getWordDate(countTwoStarWords, userId, 2, 0, countTwoStarWords.size(),true);
            getWordDate(countTwoWords, userId, 2, countTwoStarWords.size(), Math.min(10,countTwoStarWords.size()+countTwoWords.size()),false);
            result = new Result("reciteWords",reciteWordDates.get(0),reciteWordDates.get(1),reciteWordDates.get(2));

        }
        //答对一次
        else if (reciteDate.getRequestType().equals("right"))
        {
             //如果是在word_user中有数据的单词
             if (word_userDao.countAdd(wordId,userId))
             {
                 result.setStatus("countAdd");
             }
             //如果是没有数据的单词，那么就需要添加数据，并且这种情况只可能是新单词count = 0变成1的情况，所以也不需要返回指定的count
             else if(word_userDao.insertData(userId,wordId)){
                 result.setStatus("countAdd");
             }
        }
        //完成背诵，recite设置为1，count设置为3
        else if(reciteDate.getRequestType().equals("wordRecite"))
        {
            if (word_userDao.wordRecite(wordId,userId))
            {
                result.setStatus("wordRecite");
            }
        }
        //答错(如果是新单词答错，前端不会发请求给后端，只有count != 0时才会给后端发送请求，从而更改count的值)
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
        //标熟，把finish设置为1，如果是新单词没有这个数据那么会添加数据
        else if(reciteDate.getRequestType().equals("delete"))
        {

            if (word_userDao.deleteWord(wordId,userId))
            {
                result.setStatus("deleteSuccess");
            }
            else if(word_userDao.deleteNewWord(wordId,userId))
            {
                result.setStatus("deleteSuccess");
            }
        }
        //取消标熟,不用担心会没有数据，应为如果是新单词，一定要先标熟，而标熟的时候就会添加数据了
        else if(reciteDate.getRequestType().equals("undoDelete"))
        {
               if (word_userDao.undoDelete(wordId,userId))
            {
                result.setStatus("undoDeleteSuccess");
            }
        }
        //加入生词本
        else if(reciteDate.getRequestType().equals("addStar"))
        {
            StarBook starBook = new StarBook(wordId,userId);
            if (starBookDao.insert(starBook)!=0)
            {
                result.setStatus("addStar");
            }
        }
        //去除生词
        else if(reciteDate.getRequestType().equals("deleteStar"))
        {
            if (starBookDao.delete(new LambdaQueryWrapper<StarBook>().eq(StarBook::getUser_id,userId).eq(StarBook::getWord_id,wordId))!=0)
            {
                result.setStatus("deleteStar");
            }
        }
        //获取单词数
        else if(reciteDate.getRequestType().equals("getNum"))
        {
            GetNumUtil getNumUtil = new GetNumUtil();
           result.setWordNum(getNumUtil.getNum("learnNum",null,bookId,userId,null,book_wordDao,starBookDao));
           result.setStatus("learnNumSuccess");
        }
        //添加笔记
        else if(reciteDate.getRequestType().equals("addNote"))
        {
            if (noteDao.insert(new Note(wordId,userId,reciteDate.getNote()))!=0)
            {
                result.setStatus("noteAddSuccess");
            }
        }
        return result;
    }

    //整合单词显示数据
    private void getWordDate(List<Word> words,Integer userId,Integer count,Integer beg,Integer end,boolean star)
    {
        if (words.size()!=0) {
            for (int i = beg; i < end; i++) {
                String spell = words.get(i-beg).getSpell();
                Integer wordId = words.get(i-beg).getId();
                if (wordSet.get(count).isEmpty()||(!wordSet.get(count).isEmpty()&&!wordSet.get(count).contains(spell)))
                {
                    System.out.println("添加单词"+spell);
                    wordSet.get(count).add(spell);
                    reciteWordDates.get(count).add(new ReciteWordData());
                    System.out.println("添加数据后recite长度为"+reciteWordDates.get(count).size());
                    reciteWordDates.get(count).get(i).setSpell(spell);
                    reciteWordDates.get(count).get(i).setCount(count);
                    reciteWordDates.get(count).get(i).setStar(star);
                    reciteWordDates.get(count).get(i).setWordId(wordId);
                    //例句
                    List<Sentence> sentence = sentenceDao.selectList(new LambdaQueryWrapper<Sentence>().eq(Sentence::getWordId, wordId));
                    reciteWordDates.get(count).get(i).setSentence(sentence);
                    //笔记
                    List<Note> notes = noteDao.selectList(new LambdaQueryWrapper<Note>().eq(Note::getWordId, wordId).eq(Note::getUserId, userId));
                    reciteWordDates.get(count).get(i).setNotes(notes);
                    //释义
                    List<Meaning> meanings = meaningDao.selectList(new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId, wordId));
                    if (!meanings.isEmpty()) {
                        reciteWordDates.get(count).get(i).setMeaning(meanings);
                        //近义词
                        int finalI = i;
                        meanings.forEach(meaning -> {
                            synonymousUtil.getSynonymous(reciteWordDates.get(count).get(finalI), meaning, meaningDao, wordDao);
                        });
                    }
                    //派生词
                    reciteWordDates.get(count).set(i,deriveWordUtil.getDerive(meaningDao, wordDao, reciteWordDates.get(count).get(i), spell));
                    System.out.println("此时i的值为"+i);
                }
            }
            System.out.println("count"+count+star+"生词结束后reciteWordDate是"+reciteWordDates.get(count));
        }
    }
}


