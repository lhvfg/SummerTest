package com.example.Kexie.domain;


import java.sql.Time;

@lombok.Data
public class Data {
    private String requestType;
    private Integer id;
    private String userName;
    private String password;
    private Integer todayNum;
    private Integer allNum;
    private Time todayTime;
    private Time allTime;
    private Integer teamId;
    private String email;
    private String code;
}
