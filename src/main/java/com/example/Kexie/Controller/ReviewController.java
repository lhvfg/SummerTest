package com.example.Kexie.Controller;

import com.example.Kexie.Util.GetNumUtil;
import com.example.Kexie.dao.Book_wordDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.Result.ReviewResult;
import com.example.Kexie.domain.ReviewFrontDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ReviewController {
    ReviewResult result = new ReviewResult();
    @Autowired
    Book_wordDao book_wordDao;
    @Autowired
    WordDao wordDao;
    @PostMapping("/review")
    private ReviewResult review(@RequestBody ReviewFrontDate reviewFrontDate)
    {
        System.out.println(book_wordDao);
        System.out.println(wordDao);
        Integer bookId = reviewFrontDate.getBookId();
        Integer userId = reviewFrontDate.getUserId();
        if (reviewFrontDate.getRequestType().equals("getNum"))
        {
            GetNumUtil getNumUtil = new GetNumUtil();
            result.setWordNum(getNum("reviewNum",bookId,userId));
            result.setStatus("reciteNumSuccess");
        }
        return result;
    }
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
