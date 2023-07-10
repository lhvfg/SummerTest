package com.example.Kexie.domain;

import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class Result {
    private String status;
    private String userName;
    private Integer userId;
    private Integer todayNum;
    private Integer allNum;
    private Time todayTime;
    private Time allTime;
    private Integer teamId;
    private Integer wordId;
    private Integer bookId;
    private long pages;
    private List<Word> wordList;
    private List<Book> bookList;

    public Result() {
    }

    public Result(String status, String userName, Integer userId, Integer todayNum, Integer allNum, Time todayTime, Time allTime, Integer teamId, Integer bookId) {
        this.status = status;
        this.userName = userName;
        this.userId = userId;
        this.todayNum = todayNum;
        this.allNum = allNum;
        this.todayTime = todayTime;
        this.allTime = allTime;
        this.teamId = teamId;
        this.bookId = bookId;
    }

    public Result(String status) {
        this.status = status;
    }
}
