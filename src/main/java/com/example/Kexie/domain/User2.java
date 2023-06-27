package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user2")
public class User2 {
    @TableField(value="UserId")
    private int UserId;
}
