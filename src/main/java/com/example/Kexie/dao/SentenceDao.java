package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.Sentence;
import com.example.Kexie.domain.Word;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SentenceDao extends BaseMapper<Sentence> {
    @Delete("delete from sentence where word_id = #{word_id} ")
    void deleteWord(Integer wordId);
}
