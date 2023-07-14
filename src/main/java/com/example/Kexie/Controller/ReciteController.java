package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.dao.Book_userDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.dao.Word_userDao;
import com.example.Kexie.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ReciteController {
    @Autowired
    Book_userDao book_userDao;
    @Autowired
    Word_userDao word_userDao;
    @Autowired
    WordDao wordDao;
    @PostMapping("/recite")
    public Result Recitewords(@RequestBody ReciteDate reciteDate)
    {
        Result result = new Result();
        Integer userId = reciteDate.getUserId();
        LambdaQueryWrapper<Word_user> lqw = new LambdaQueryWrapper<>();
        if(reciteDate.getRequestType().equals("getWords"))
        {
            List<Word> newWords = wordDao.selectNewWords(userId);
            List<Word_user> countOneWords = word_userDao.selectList(new LambdaQueryWrapper<Word_user>().eq(Word_user::getCount, 1).eq(Word_user::getUserId,userId));
            List<Word_user> countTwoWords = word_userDao.selectList(new LambdaQueryWrapper<Word_user>().eq(Word_user::getCount, 2).eq(Word_user::getUserId,userId));

        }
        return result;
    }
//    private Result getWordDate(Integer wordId,String spell)
//    {
//        newWords.forEach(newWord->{
//            result[0] = getWordDate(newWord.getId(), newWord.getSpell());
//        });
//        return result;
//    }
}
