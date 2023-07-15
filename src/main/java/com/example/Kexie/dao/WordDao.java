package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordDao extends BaseMapper<Word> {
    @Select("SELECT spell,w.id FROM word w LEFT JOIN word_user wu ON w.id = wu.word_id AND wu.user_id = #{id} WHERE wu.id IS NULL")
    List<Word> selectNewWords(Integer id);
}
