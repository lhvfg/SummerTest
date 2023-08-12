package com.example.Kexie.domain.Result;

import com.example.Kexie.domain.BasicPojo.Book;
import com.example.Kexie.domain.BasicPojo.Word;
import com.example.Kexie.domain.ReciteWordData;
import lombok.Data;

import java.sql.Time;
import java.util.ArrayList;
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
    private ArrayList<ReciteWordData> reciteNewWordDates;
    private ArrayList<ReciteWordData> reciteOneWordDates;
    private ArrayList<ReciteWordData> reciteTwoWordDates;
    private Integer wordNum;
    private String lastClockinTime;
    private Integer accumulateDay;

    public Result() {
    }

    public Result(String status, String userName, Integer userId, Integer todayNum, Integer allNum, Time todayTime, Time allTime, Integer teamId, Integer bookId,String lastClockinTime,Integer accumulateDay) {
        this.status = status;
        this.userName = userName;
        this.userId = userId;
        this.todayNum = todayNum;
        this.allNum = allNum;
        this.todayTime = todayTime;
        this.allTime = allTime;
        this.teamId = teamId;
        this.bookId = bookId;
        this.lastClockinTime = lastClockinTime;
        this.accumulateDay = accumulateDay;
    }

    public Result(String status) {
        this.status = status;
    }

    public Result(String status, ArrayList<ReciteWordData> reciteNewWordDates, ArrayList<ReciteWordData> reciteOneWordDates, ArrayList<ReciteWordData> reciteTwoWordDates) {
        this.status = status;
        this.reciteNewWordDates = reciteNewWordDates;
        this.reciteOneWordDates = reciteOneWordDates;
        this.reciteTwoWordDates = reciteTwoWordDates;
    }
}
