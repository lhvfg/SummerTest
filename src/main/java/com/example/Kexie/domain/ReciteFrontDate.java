package com.example.Kexie.domain;

import lombok.Data;

@Data
//获取前端数据
public class ReciteFrontDate {
    private String RequestType;
    private Integer userId;
    private Integer wordId;
    //背了几个
    private Integer number;
    //学了多少秒
    private Integer time;
    //在背那一本
    private Integer bookId;
}
