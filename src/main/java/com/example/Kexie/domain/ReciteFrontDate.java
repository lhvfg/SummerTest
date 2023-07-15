package com.example.Kexie.domain;

import lombok.Data;

@Data
//获取前端数据
public class ReciteFrontDate {
    private String RequestType;
    private Integer userId;
    private Integer bookId;
    private String[] words;
    private Integer count;
}
