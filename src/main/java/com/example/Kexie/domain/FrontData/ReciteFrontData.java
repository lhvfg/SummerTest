package com.example.Kexie.domain.FrontData;

import lombok.Data;

@Data
//获取前端数据
public class ReciteFrontData {
    private String RequestType;
    private Integer userId;
    private Integer wordId;
    private Integer count;
    //背了几个
    private Integer number;
    //学了多少秒
    private Integer time;
    //在背那一本
    private Integer bookId;
    //添加的笔记内容
    private String note;
}
