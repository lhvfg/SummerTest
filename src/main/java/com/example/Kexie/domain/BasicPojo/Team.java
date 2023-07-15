package com.example.Kexie.domain.BasicPojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Team {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String teamName;
    private Integer num;
}
