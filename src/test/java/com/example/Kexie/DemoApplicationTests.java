package com.example.Kexie;

import com.example.Kexie.dao.BookDao3;
import com.example.Kexie.dao.UserDao;
import com.example.Kexie.domain.Book;
import com.example.Kexie.domain.Result;
import com.example.Kexie.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
@RestController
class DemoApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BookDao3 bookDao3;
    @Test
    public void GetAll()
    {
        User user = userDao.findById(1);
        User user1 = userDao.selectById(1);
        System.out.println(user1);
    };
    @Test
    public void GetBook()
    {
       Book book1 =bookDao3.selectById(1);
       System.out.println(book1);
    }
    @Test
    @PostMapping("/register")
    public Result add (@RequestBody User user)
    {
        Result result = new Result();
        if (user.getUserName()!=null&&user.getPassword()!=null)
        {
            userDao.insert(user);
            result.setStatus("registerSucceed");
        }
        else {
            result.setStatus("registerFail");
        }
        return result;
    }
}
