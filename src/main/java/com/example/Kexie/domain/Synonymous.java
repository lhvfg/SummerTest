package com.example.Kexie.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Synonymous {
    private String Function;
    private String meaning;
    private ArrayList<String> spells;

    public Synonymous() {
    }

    public Synonymous(String function, String meaning, ArrayList<String> spells) {
        Function = function;
        this.meaning = meaning;
        this.spells = spells;
    }
}
