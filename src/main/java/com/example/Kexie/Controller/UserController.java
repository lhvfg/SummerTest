package com.example.Kexie.Controller;

import com.example.Kexie.dao.UserDao;
import com.example.Kexie.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

@PostMapping("/add")
void add ()
{

}

    @Autowired
    UserDao userDao;
}
