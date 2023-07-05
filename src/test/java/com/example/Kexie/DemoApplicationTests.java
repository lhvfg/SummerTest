package com.example.Kexie;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kexie.dao.BookDao;
import com.example.Kexie.dao.UserDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.Book;
import com.example.Kexie.domain.Result;
import com.example.Kexie.domain.User;

import com.example.Kexie.domain.Word;
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
    private BookDao bookDao3;
    @Autowired
    private WordDao wordDao;
    @Test
    public void PageSelect(){
        IPage<Word> page=new Page<>(1,2);
        wordDao.selectPage(page,null);
        long pages= page.getPages();
        long i=pages;
        do {
            i-=1;
            //2 执行分页查询
            IPage<Word> page2=new Page<>(pages-i,2);
            System.out.println("方法返回值是："+wordDao.selectPage(page2,null));
            page2.getRecords().forEach(System.out::println);
        }while(i>0);

    }

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
