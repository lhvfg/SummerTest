package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.Meaning;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeaningDao extends BaseMapper<Meaning> {
    @Delete("delete from sentence where word_id = #{word_id} ")
    void deleteWord(Integer wordId);
}
