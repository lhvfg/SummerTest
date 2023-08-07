package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class Word_user {
    @TableId(type = IdType.AUTO)
    private Integer id ;
    private Integer recite;
    private Date nextReview;
    private Integer stage;
    private Integer count;
    @TableLogic(value = "0",delval = "1")//逻辑删除，用MP进行查询时会自动带上where finish=0的条件
    private Integer finish;
    private Integer userId;
    private Integer wordId;

    public Word_user() {
    }

    public Word_user(Integer id) {
        this.id = id;
    }

    public Word_user(Date nextReview, Integer stage) {
        this.nextReview = nextReview;
        this.stage = stage;
    }
}
