package com.example.Kexie.domain;

import com.example.Kexie.domain.BasicPojo.Meaning;
import com.example.Kexie.domain.BasicPojo.Note;
import com.example.Kexie.domain.BasicPojo.Sentence;
import com.example.Kexie.domain.BasicPojo.Word;
import lombok.Data;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
//封装背诵单词要显示出来的数据
public class ReciteWordData {
    private Integer wordId;
    private String spell;
    private Integer count;
    private ArrayList<Synonymous> Synonymous;//近义词 相同词性且中文释义相同 返回释义->所有的单词拼写
    private Derive[] Derived;//派生词 相似的单词 单词拼写->所有的释义
    //例句
    private List<Sentence> sentence;
    //释义数据
    private List<Meaning> meaning;
    //笔记
    private List<Note> notes;
    //是否是生词
    private boolean star;
}
