package com.example.Kexie.domain;

import lombok.Data;

import java.sql.Time;

@Data
public class Word_user {
    private Integer id  ;
    private Integer recite;
    private Time nextReview;
    private Time lastReview;
    private Integer count;
    private Integer finish;
    private Integer noteNum;
    private Integer userId;
    private Integer wordId;
}
