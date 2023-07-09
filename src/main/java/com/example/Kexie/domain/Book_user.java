package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Book_user {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer bookId;
    private Integer userId;

    public Book_user(Integer bookId, Integer userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public Book_user() {
    }
}
