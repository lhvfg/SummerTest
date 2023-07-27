package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Meaning {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer wordId;
    private String content;
    private String function;

    public Meaning() {
    }

    public Meaning(Integer wordId, String content, String function) {
        this.wordId = wordId;
        this.content = content;
        this.function = function;
    }
}
