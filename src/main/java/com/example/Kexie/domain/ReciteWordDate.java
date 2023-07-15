package com.example.Kexie.domain;

import com.example.Kexie.domain.BasicPojo.Meaning;
import com.example.Kexie.domain.BasicPojo.Note;
import com.example.Kexie.domain.BasicPojo.Sentence;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
//封装背诵单词要显示出来的数据
public class ReciteWordDate {
    private String spell;
    private Integer count;
    private ArrayList<String> Synonymous;//近义词 相同词性且中文释义相同
    private ArrayList<String> Derived;//派生词 相似的单词拼写
    //例句
    private Sentence sentence;
    //释义数据
    private Meaning meaning;
    //笔记
    private List<Note> notes;
}
