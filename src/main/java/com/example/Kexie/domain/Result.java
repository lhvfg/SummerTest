package com.example.Kexie.domain;

import lombok.Data;

import java.sql.Time;

@Data
public class Result {
    private String status;
    private String userName;
    private Integer todayNum;
    private Integer allNum;
    private Time todayTime;
    private Time allTime;
    private Integer teamId;
}
