package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.FrontData.DashBoardFrontData;
import com.example.Kexie.domain.Result.DashBoardResult;
import com.example.Kexie.domain.Result.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class DashBoardController {
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
    UserDao userDao;
    DashBoardResult result = new DashBoardResult();
    @PostMapping("/dashboard")
    public DashBoardResult getDashBoardData(@RequestBody DashBoardFrontData frontData) {
        Integer userId = frontData.getUserId();
        Integer bookId = frontData.getBookId();
        Long bookWordNum = book_wordDao.selectCount(new LambdaQueryWrapper<Book_word>().eq(Book_word::getBook_id,8));
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
}
