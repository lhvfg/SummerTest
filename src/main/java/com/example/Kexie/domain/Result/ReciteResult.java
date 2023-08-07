package com.example.Kexie.domain.Result;

import com.example.Kexie.domain.ReciteWordData;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ReciteResult {
    private String status;
    private Integer wordNum;
    private ArrayList<ReciteWordData> reviewWords;
}
