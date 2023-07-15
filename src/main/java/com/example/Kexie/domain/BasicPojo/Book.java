package com.example.Kexie.domain.BasicPojo;

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
 private Integer userId;

 public Book(String bookName, Integer hide, Integer wordNum) {
  this.bookName = bookName;
  this.hide = hide;
  this.wordNum = wordNum;
 }

 public Book(String bookName, Integer hide, Integer wordNum, Integer userId) {
  this.bookName = bookName;
  this.hide = hide;
  this.wordNum = wordNum;
  this.userId = userId;
 }

 public Book(Integer id, Integer wordNum) {
  this.id = id;
  this.wordNum = wordNum;
 }

 public Book() {
 }
}
