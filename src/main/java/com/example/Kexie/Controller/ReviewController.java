package com.example.Kexie.Controller;

import com.example.Kexie.Util.GetNumUtil;
import com.example.Kexie.dao.Book_userDao;
import com.example.Kexie.dao.Book_wordDao;
import com.example.Kexie.dao.TeamDao;
import com.example.Kexie.dao.UserDao;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.Result.ReciteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class ReviewController {

    @Autowired
    UserDao userDao;
    @Autowired
    Book_userDao book_userDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    Book_wordDao book_wordDao;
    @PostMapping("/review")
    public ReciteResult review (@RequestBody ReviewFrontDate reviewDate)
    {
        ReciteResult result = new ReciteResult();
        Integer bookId = reviewDate.getBookId();
        Integer userId = reviewDate.getUserId();
        if (reviewDate.getRequestType().equals("getNum"))
        {
            GetNumUtil getNumUtil = new GetNumUtil();
            result.setWordNum(getNum("reviewNum",bookId,userId));
            result.setStatus("reciteNumSuccess");
        }
        return result;
    };
    private Integer getNum(String type, Integer bookId, Integer userId)
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

