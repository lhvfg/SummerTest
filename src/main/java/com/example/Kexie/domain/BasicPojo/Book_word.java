package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book_word")
public class Book_word {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer word_id;
    private Integer book_id;

    public Book_word(Integer word_id, Integer book_id) {
        this.word_id = word_id;
        this.book_id = book_id;
    }

    public Book_word() {
    }
}
