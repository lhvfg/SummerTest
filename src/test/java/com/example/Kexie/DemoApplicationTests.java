package com.example.Kexie;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.Result.Result;

import com.example.Kexie.domain.WordData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.sql.Date;
import java.sql.Time;

@SpringBootTest
@RestController
class DemoApplicationTests {
    @Autowired
    private Word_userDao word_userDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void l(){
        System.out.println(1);
        System.out.println(userDao.selectById(3));
    }
    @Test
    public void print(){

        System.out.println(1);
    }
}
