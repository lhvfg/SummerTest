package com.example.Kexie.Util;

import com.example.Kexie.dao.Book_wordDao;
import com.example.Kexie.dao.StarBookDao;
import com.example.Kexie.dao.WordDao;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;


@Transactional
public class GetNumUtil {
    public Integer getNum(String type, String today, Integer bookId, Integer userId, WordDao wordDao, Book_wordDao book_wordDao, StarBookDao starBookDao)
    {
        Integer ans=0;
        if (type.equals("learnNum"))
        {
            ans= book_wordDao.getBookLearnNum(bookId,userId)+starBookDao.getLearnStarNum(bookId,userId);
        }
        else if (type.equals("reviewNum"))
        {
            Date t = Date.valueOf(today);
            ans= wordDao.getReviewNum(bookId,userId,t);
        }
        return ans;
    }
}
