package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Time;

@Data
@TableName("user")
public class User {
    private Integer UserId;
    private String UserName;
    private String Password;
    private int TodayNum;
    private int AllNum;
    private Time TodayTime;
    private Time AllTime;
    private int TeamId;
//  @TableField(value="id")
//    @TableField("UserName")
//    @TableField("Password")
//    @TableField("TodayNum")
//    @TableField("AllNum")
//    @TableField("TodayTime")
//    @TableField("AllTime")
//    @TableField("TeamId")


}
