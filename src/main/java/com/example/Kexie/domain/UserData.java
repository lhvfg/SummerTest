package com.example.Kexie.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Time;

@lombok.Data
// 用户相关数据
public class UserData {
    private Integer id;
    private String requestType;
    private String userName;
    private String password;
    private Integer todayNum;
    private Integer allNum;
    private Time todayTime;
    private Time allTime;
    private Integer teamId;
    private String email;
    private String code;
}
