package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kexie.dao.BookDao;
import com.example.Kexie.dao.Book_wordDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class BookController {
    @Autowired
    BookDao bookDao;
    @Autowired
    WordDao wordDao;
    @Autowired
    Book_wordDao book_wordDao;
    @PostMapping("/addBook")
    public Result addBook(@RequestBody BookDate bookDate)
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
            result.setPages(page.getPages()); // 总页数
            result.setList(page.getRecords());
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
            Integer bookId = bookDate.getBookId();
            Book book = new Book(bookDate.getBookName(),bookDate.getHide(),bookDate.getWordNum());
            Book_word book_word = new Book_word();
        }
        return result;
    }
}
