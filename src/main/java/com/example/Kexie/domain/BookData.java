package com.example.Kexie.domain;

import lombok.Data;

@Data
public class BookData {
    private Integer id      ;
    private String requestType;
    private String bookName    ;
    private Integer hide         ;
    private Integer wordNum    ;
    private Integer[] wordId;
    private Integer bookId;
    private Integer pageNumber;
    private Integer userId;
}
