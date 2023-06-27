package com.example.Kexie;

import com.example.Kexie.dao.BookDao3;
import com.example.Kexie.dao.UserDao;
import com.example.Kexie.dao.UserDao2;
import com.example.Kexie.domain.Book;
import com.example.Kexie.domain.User;

import com.example.Kexie.domain.User2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDao2 userDao2;
    @Autowired
    private BookDao3 bookDao3;
    @Test
    public void GetAll()
    {
        User2 user2 = userDao2.find(1);
        System.out.println(user2);
//        User user = userDao.find(1);
//        System.out.println(user);
    };
    @Test
    public void GetBook()
    {
 //     Book books= bookDao3.find1(1);
       Book book1 =bookDao3.selectById(1);
       System.out.println(book1);
    }
//    @Test
//    public void Up()
//    {
//        User user  = new User();
//
//        userDao.update()
//    }
}
