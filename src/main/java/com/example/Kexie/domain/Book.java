package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book")
public class Book {
 @TableId(type = IdType.AUTO)
 private Integer id      ;
 private String bookName    ;
 private Integer hide         ;
 private Integer wordNum    ;
}
