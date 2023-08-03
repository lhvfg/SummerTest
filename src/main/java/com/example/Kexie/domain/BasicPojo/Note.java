package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Note {
    @TableId(type = IdType.AUTO)
private Integer id;
private Integer wordId;
private Integer userId;
private String content;

    public Note() {
    }

    public Note(Integer wordId, Integer userId, String content) {
        this.wordId = wordId;
        this.userId = userId;
        this.content = content;
    }
}
