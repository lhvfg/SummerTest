package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kexie.dao.BookDao;
import com.example.Kexie.dao.Book_userDao;
import com.example.Kexie.dao.Book_wordDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
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
public class BookController {
    @Autowired
    BookDao bookDao;
    @Autowired
    Book_wordDao book_wordDao;
    @Autowired
    WordDao wordDao;
    @Autowired
    Book_userDao book_userDao;
    @PostMapping("/addBook")
    public Result addBook(@RequestBody BookData bookDate)
    {
        Result result = new Result();
        QueryWrapper<Book> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        // 进入页面，获取单词列表
        if(bookDate.getRequestType().equals("getWordList"))
        {
            //1 创建IPage分页对象,设置分页参数,1为当前页码，20为每页显示的记录数
            IPage<Word> page=new Page<>(bookDate.getPageNumber(),15);
            //2 执行分页查询
            wordDao.selectPage(page,null);
            //3 获取分页结果
            result.setStatus("wordList");
            result.setPages(page.getPages()); // 总页数
            result.setWordList(page.getRecords());
        }
        //判断重名
        else if(bookDate.getRequestType().equals("addBookRequest"))
        {
            lqw.eq(Book::getBookName, bookDate.getBookName());
            Book book = bookDao.selectOne(lqw);
            if (book != null)
            {
                result.setStatus("bookRename");
            }
            else{
                qw.select("max(id) as id");
                result.setStatus("bookNotExist");
                result.setBookId(bookDao.selectOne(qw).getId());
            }
        }
        //添加单词书
        else if(bookDate.getRequestType().equals("addBook"))
        {
            Integer[] wordId = bookDate.getWordId();
            Integer wordNum = bookDate.getWordNum();
            Integer bookId = bookDate.getBookId();
            Integer userId = bookDate.getUserId();
            Book book = new Book(bookDate.getBookName(),bookDate.getHide(),wordNum,userId);
            if (wordId!=null)
            for (int i=0;i<wordNum;i++) {
                Book_word book_word = new Book_word(wordId[i],bookId);
                book_wordDao.insert(book_word);
            }
            bookDao.insert(book);
            result.setStatus("addBookSucceed");
        }
        return result;
    }
    @PostMapping("/chooseBook")
    public Result chooseBook(@RequestBody BookData bookDate, HttpSession httpSession)
    {
        //System.out.println(httpSession+"地址中的userId为"+httpSession.getAttribute("userId"));
        Result result = new Result();
        if(bookDate.getRequestType().equals("chooseBookRequest")) {
            List<Book> books = bookDao.selectList(null);
            result.setBookList(books);
            result.setStatus("bookList");
        }
        else if(bookDate.getRequestType().equals("chooseBookAdd")){
            Book_user book_user = new Book_user(bookDate.getBookId(),bookDate.getUserId());
            book_userDao.insert(book_user);
            result.setStatus("chooseSucceed");
        }
        else if(bookDate.getRequestType().equals("chooseBookUpdate")){
            Book_user book_user = new Book_user(bookDate.getBookId(),bookDate.getUserId());
            book_userDao.update(book_user,new LambdaQueryWrapper<Book_user>().eq(Book_user::getUserId,bookDate.getUserId()));
            result.setStatus("chooseSucceed");
        }
        return result;
    }
}
