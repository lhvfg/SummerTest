package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.FrontData.DashboardData;
import com.example.Kexie.domain.FrontData.MyContentData;
import com.example.Kexie.domain.Result.DashBoardResult;
import com.example.Kexie.domain.Result.MyContentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@Transactional
public class DataController {
    ArrayList<ContentWordData> unRecite;
    ArrayList<ContentWordData> learning;
    ArrayList<ContentWordData> recited;
    ArrayList<ContentWordData> finish;
    ArrayList<ContentWordData> notes;
    @Autowired
    BookDao bookDao;
    @Autowired
    StarBookDao starBookDao;
    @Autowired
    Book_wordDao book_wordDao;
    @Autowired
    WordDao wordDao;
    @Autowired
    Word_userDao word_userDao;
    @Autowired
    Book_userDao book_userDao;
    @Autowired
    SentenceDao sentenceDao;
    @Autowired
    NoteDao noteDao;
    @Autowired
    MeaningDao meaningDao;
    @Autowired
    UserDao userDao;
    @PostMapping("/dashboard")
    public DashBoardResult getDashBoardData(@RequestBody DashboardData frontData) {
        DashBoardResult result = new DashBoardResult();
        Integer userId = frontData.getUserId();
        Integer bookId = frontData.getBookId();
        Long bookWordNum = book_wordDao.selectCount(new LambdaQueryWrapper<Book_word>().eq(Book_word::getBook_id,bookId));
        User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getId,userId));
        String bookName = bookDao.selectOne(new LambdaQueryWrapper<Book>().eq(Book::getId,bookId)).getBookName();
        Long starNum = starBookDao.selectCount(new LambdaQueryWrapper<StarBook>().eq(StarBook::getUser_id,userId));
        Integer recitedNum =wordDao.getRecitedNum(bookId,userId);
        Long allStudyNum = starNum+bookWordNum;
        Long allRecitedNum = word_userDao.selectCount(new LambdaQueryWrapper<Word_user>().eq(Word_user::getUserId,userId).eq(Word_user::getRecite,1));
        DashBoardData data = new DashBoardData(bookName,starNum,recitedNum,allStudyNum,user.getTodayNum(),allRecitedNum,user.getTodayTime(),user.getAllTime());
        result.setStatus("getDataSuccess");
        result.setDashBoardData(data);
        return result;
    }
    @PostMapping("/mycontent")
    public MyContentResult getMyContentData(@RequestBody MyContentData frontData){
        MyContentResult result = new MyContentResult();
        Integer userId = frontData.getUserId();
        Integer bookId = frontData.getBookId();
        if(frontData.getRequestType().equals("getData"))
        {
            Long bookWordNum = book_wordDao.selectCount(new LambdaQueryWrapper<Book_word>().eq(Book_word::getBook_id, bookId));
            Long allRecitedNum = word_userDao.selectCount(new LambdaQueryWrapper<Word_user>().eq(Word_user::getUserId, userId).eq(Word_user::getRecite, 1));
            Long starNum = starBookDao.selectCount(new LambdaQueryWrapper<StarBook>().eq(StarBook::getUser_id, userId));
            Long noteNum = noteDao.selectCount(new LambdaQueryWrapper<Note>().eq(Note::getUserId, userId));
            ContentNumData contentNumData = new ContentNumData(bookWordNum, allRecitedNum, starNum, noteNum);
            result =  new MyContentResult("getDataSuccess", contentNumData);
        }
        else if(frontData.getRequestType().equals("getBookData")){
            clearArrayList();
            List<Word> _unRecite = wordDao.selectUnRecite(bookId,userId);
            //_unRecite.addAll(wordDao.selectLearning(0,userId,bookId));
            List<Word> _learning = wordDao.selectLearning(1,userId,bookId);
            _learning.addAll(wordDao.selectLearning(2,userId,bookId));
            List<Word> _recited = wordDao.selectRecitedWord(userId, bookId);
            List<Word> _finish = wordDao.selectFinishedWord(userId, bookId);
            getWordData(_unRecite,0,userId,false);
            getWordData(_learning,1,userId,false);
            getWordData(_recited,2,userId,false);
            getWordData(_finish,3,userId,false);
            result = new MyContentResult("getBookDataSuccess",bookDao.selectOne(new LambdaQueryWrapper<Book>().eq(Book::getId,bookId)).getBookName(),unRecite,learning,recited,finish);
        }
        else if(frontData.getRequestType().equals("getRecited"))
        {
            clearArrayList();
            List<Word> _recited = wordDao.selectAllRecitedWord(userId);
            List<Word> _finish = wordDao.selectAllFinishedWord(userId);
            getWordData(_recited,2,userId,false);
            getWordData(_finish,2,userId,false);
            result = new MyContentResult("getRecitedWordSuccess",recited,finish);
        }
        else if(frontData.getRequestType().equals("getStar"))
        {
            clearArrayList();
            List<Word> _unRecite = wordDao.selectNewStarWords(userId,bookId);
            //_unRecite.addAll(wordDao.selectCountStarWords(0,userId));
            List<Word> _learning = wordDao.selectCountStarWords(1,userId);
            _learning.addAll(wordDao.selectCountStarWords(2,userId));
            List<Word> _recited = wordDao.selectRecitedWord(userId, bookId);
            List<Word> _finish = wordDao.selectFinishedWord(userId, bookId);
            getWordData(_unRecite,0,userId,true);
            getWordData(_learning,1,userId,true);
            getWordData(_recited,2,userId,true);
            getWordData(_finish,3,userId,true);
            result = new MyContentResult("getStarSuccess",unRecite,learning,recited,finish);
        }
        else if(frontData.getRequestType().equals("getNote")){
            clearArrayList();
            List<Note> ns = noteDao.selectList(new LambdaQueryWrapper<Note>().eq(Note::getUserId,userId));
            Set<String> wordSet = new TreeSet<>();
            ns.forEach(note -> {
                String spell = wordDao.getSpell(note.getWordId());
                if (wordSet.isEmpty()||!wordSet.contains(spell))
                {
                    wordSet.add(spell);
                    notes.add(new ContentWordData(spell,noteDao.selectList(new LambdaQueryWrapper<Note>().eq(Note::getWordId,note.getWordId()).eq(Note::getUserId,userId))));
                }
            });
            result = new MyContentResult("getNote",notes);
        }
            return result;
    }
    private void getWordData(List<Word> words,Integer type,Integer userId,boolean starOnly){
        words.forEach(word -> {
            List<Meaning> meanings = meaningDao.selectList(new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId,word.getId()));
            boolean star;
            star = starBookDao.selectOne(new LambdaQueryWrapper<StarBook>().eq(StarBook::getUser_id, userId).eq(StarBook::getWord_id, word.getId())) != null;
            if (star||!starOnly){
                switch (type) {
                    case 0 -> unRecite.add(new ContentWordData(word.getId(),word.getSpell(), meanings, star));
                    case 1 -> learning.add(new ContentWordData(word.getId(),word.getSpell(), meanings, star));
                    case 2 -> recited.add(new ContentWordData(word.getId(),word.getSpell(), meanings, star));
                    case 3 -> finish.add(new ContentWordData(word.getId(),word.getSpell(), meanings, star));
                }
            }
        });
    }
    private void clearArrayList(){
        //初始化
        unRecite = new ArrayList<>();
        learning = new ArrayList<>();
        recited = new ArrayList<>();
        finish = new ArrayList<>();
        notes = new ArrayList<>();
    }
}
