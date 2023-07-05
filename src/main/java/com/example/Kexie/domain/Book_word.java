package com.example.Kexie.domain;

import lombok.Data;

@Data
public class Book_word {
    private Integer id;
    private Integer word_id;
    private Integer book_id;

    public Book_word(Integer word_id, Integer book_id) {
        this.word_id = word_id;
        this.book_id = book_id;
    }

    public Book_word() {
    }
}
