package com.example.Kexie.domain;

import com.example.Kexie.domain.BasicPojo.Meaning;
import com.example.Kexie.domain.BasicPojo.Note;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContentWordData {
    private String spell;
    private List<Meaning> meanings;
    private List<Note> notes;
    boolean star;

    public ContentWordData() {
    }

    public ContentWordData(String spell, List<Meaning> meanings, boolean star) {
        this.spell = spell;
        this.meanings = meanings;
        this.star = star;
    }

    public ContentWordData(String spell, List<Note> notes) {
        this.spell = spell;
        this.notes = notes;
    }
}
