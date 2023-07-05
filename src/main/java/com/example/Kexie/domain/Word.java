package com.example.Kexie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;

@Data
public class Word {
    @TableId(type = IdType.AUTO)
private Integer id  ;
private String spell;



}
