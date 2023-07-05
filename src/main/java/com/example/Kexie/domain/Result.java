package com.example.Kexie.domain;

import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class Result {
    private String status;
    private String userName;
    private Integer todayNum;
    private Integer allNum;
    private Time todayTime;
    private Time allTime;
    private Integer teamId;
    private Integer wordId;
    private Integer bookId;
    private long pages;
    private List<Word> list;
}
