package com.example.Kexie.Controller;

import com.example.Kexie.dao.UserDao;
import com.example.Kexie.domain.Result;
import com.example.Kexie.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserDao userDao;
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
