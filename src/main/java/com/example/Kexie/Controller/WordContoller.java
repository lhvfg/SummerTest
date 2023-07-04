package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.Result;
import com.example.Kexie.domain.User;
import com.example.Kexie.domain.Word;
import com.example.Kexie.domain.WordDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class WordContoller {
    @Autowired
    WordDao wordDao;
    @PostMapping("/addword")
    public Result addWordRequest(@RequestBody WordDate wordDate){
        Result result = new Result();
        QueryWrapper<Word> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Word> lqw = new LambdaQueryWrapper<>();
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
                result.setWordId(wordDao.selectOne(qw).getId());
                System.out.println(wordDao.selectOne(qw).getId());
            }
        }
        else if(wordDate.getRequestType().equals("addWord"))
        {
            Word word = new Word();
        }
        return result;
    };

}
