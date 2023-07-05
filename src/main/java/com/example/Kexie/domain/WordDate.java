package com.example.Kexie.domain;

import lombok.Data;

import java.sql.Time;

@Data
//单词数据
public class WordDate {
    private String requestType;

    private String spell;
    private Integer recite;
    private Time nextReview;
    private Time lastReview;
    private Integer count;
    private Integer finish;
    //是否为新单词
    private Integer wordType;
    //释义、例句、笔记的数量
    private Integer meaningNum;
    private Integer noteNum;
    private Integer sentenceNum;
    //共享数据 单词序号
    private Integer wordId;
    //释义数据
    private String[] meaningContent;
    private String[] function;
    //例句
    private String[] sentenceContent;
    private String[] sentenceContentMean;
    //笔记
    private String[] noteContent;
    //选择的单词书序号
    private Integer bookId;
}
