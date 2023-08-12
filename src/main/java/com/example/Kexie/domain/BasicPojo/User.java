package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Time;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private Integer todayNum;
    private Integer allNum;
    private Time todayTime;
    private Time allTime;
    private Integer teamId;
    private String email;
    private String lastLoginTime;
    private String lastClockinTime;
    private Integer accumulateDay;
    private Integer successiveDay;

    public User() {
    }

    public User(Integer todayNum, Time todayTime) {
        this.todayNum = todayNum;
        this.todayTime = todayTime;
    }

    public User(Integer todayNum, Time todayTime, String lastLoginTime) {
        this.todayNum = todayNum;
        this.todayTime = todayTime;
        this.lastLoginTime = lastLoginTime;
    }

    public User(String lastClockinTime, Integer accumulateDay) {
        this.lastClockinTime = lastClockinTime;
        this.accumulateDay = accumulateDay;
    }
}
