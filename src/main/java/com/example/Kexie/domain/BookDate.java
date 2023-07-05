package com.example.Kexie.domain;

import lombok.Data;

@Data
public class BookDate {
    private Integer id      ;
    private String requestType;
    private String bookName    ;
    private Integer hide         ;
    private Integer wordNum    ;
    private Integer[] wordId;
    private Integer bookId;
}
