package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Sentence {
    @TableId(type = IdType.AUTO)
private Integer id;
private Integer wordId;
private String content;
private String contentMean;

    public Sentence() {
    }

    public Sentence(Integer wordId, String content, String contentMean) {
        this.wordId = wordId;
        this.content = content;
        this.contentMean = contentMean;
    }
}
