package com.example.Kexie.Util;

import com.example.Kexie.dao.Book_wordDao;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GetNumUtil {
    public Integer getNum(String type, Integer bookId, Integer userId, Book_wordDao book_wordDao)
    {
        Integer ans=0;
        if (type.equals("learnNum"))
        {
            ans= book_wordDao.getLearnNum(bookId,userId);
        }
        else if (type.equals("reviewNum"))
        {
            ans= book_wordDao.getReviewNum(bookId,userId);
        }
        return ans;
    }
}
