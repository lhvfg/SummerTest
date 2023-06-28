package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Time;

@Data
@TableName("user")
public class User {
    private Integer id;
    private String userName;
    private String password;
    private Integer todayNum;
    private Integer allNum;
    private Time todayTime;
    private Time allTime;
    private Integer teamId;
    private String email;
//  @TableField(value="id")
//    @TableField("UserName")
//    @TableField("Password")
//    @TableField("TodayNum")
//    @TableField("AllNum")
//    @TableField("TodayTime")
//    @TableField("AllTime")
//    @TableField("TeamId")


}
