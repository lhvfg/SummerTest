package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book")
public class Book {
 private Integer id      ;
 private String bookName    ;
 private Integer hide         ;
 private Integer wordNum    ;
}
