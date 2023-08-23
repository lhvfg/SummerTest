package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;

@Data
public class Word {
    @TableId(type = IdType.AUTO)
private Integer id  ;
private String spell;

    public Word(Integer id, String spell) {
        this.id = id;
        this.spell = spell;
    }

    public Word() {
    }

    public Word(String spell) {
        this.spell = spell;
    }
}
