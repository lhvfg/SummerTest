package com.example.Kexie.domain;

import lombok.Data;

@Data
public class ContentNumData {
    private Long bookNum ;
    private Long allRecitedNum ;
    private Long starNum;
    private Long noteNum;

    public ContentNumData(Long bookNum, Long allRecitedNum, Long starNum, Long noteNum) {
        this.bookNum = bookNum;
        this.allRecitedNum = allRecitedNum;
        this.starNum = starNum;
        this.noteNum = noteNum;
    }

    public ContentNumData() {
    }
}

