package com.example.Kexie.domain;

import lombok.Data;

@Data
public class ReciteDate {
    private String RequestType;
    private Integer userId;
    private Integer bookId;
    private String[] words;
    private Integer count;
}
