package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;

@Data
public class Word {
    @TableId(type = IdType.AUTO)
private Integer id  ;
private String spell;
private Integer recite;
private Time nextReview;
private Time lastReview;
private Integer count;
private Integer finish;
private Integer noteNum;

    public Word() {
    };

    public Word(String spell, Integer recite, Time nextReview, Time lastReview, Integer count, Integer finish, Integer noteNum) {
        this.spell = spell;
        this.recite = recite;
        this.nextReview = nextReview;
        this.lastReview = lastReview;
        this.count = count;
        this.finish = finish;
        this.noteNum = noteNum;
    }

    public Word(String spell, Integer noteNum) {
        this.spell = spell;
        this.noteNum = noteNum;
    }
}
