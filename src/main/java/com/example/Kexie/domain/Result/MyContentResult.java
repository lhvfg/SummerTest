package com.example.Kexie.domain.Result;

import com.example.Kexie.domain.BasicPojo.Note;
import com.example.Kexie.domain.ContentNumData;
import com.example.Kexie.domain.ContentWordData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyContentResult {
    private String status;
    private ContentNumData contentNumData;
    private ArrayList<ContentWordData> unRecite;
    private ArrayList<ContentWordData> learning;
    private ArrayList<ContentWordData> recited;
    private ArrayList<ContentWordData> finish;
    private ArrayList<ContentWordData> notes;

    public MyContentResult() {
    }

    public MyContentResult(String status, ContentNumData contentNumData) {
        this.status = status;
        this.contentNumData = contentNumData;
    }

    public MyContentResult(String status, ArrayList<ContentWordData> unRecite, ArrayList<ContentWordData> learning, ArrayList<ContentWordData> recited, ArrayList<ContentWordData> finish) {
        this.status = status;
        this.unRecite = unRecite;
        this.learning = learning;
        this.recited = recited;
        this.finish = finish;
    }

    public MyContentResult(String status, ArrayList<ContentWordData> recited, ArrayList<ContentWordData> finish) {
        this.status = status;
        this.recited = recited;
        this.finish = finish;
    }

    public MyContentResult(String status, ArrayList<ContentWordData> notes) {
        this.status = status;
        this.notes = notes;
    }
}
