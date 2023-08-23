package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class StarBook {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer word_id;
    private Integer user_id;

    public StarBook() {
    }

    public StarBook(Integer word_id, Integer user_id) {
        this.word_id = word_id;
        this.user_id = user_id;
    }
}
