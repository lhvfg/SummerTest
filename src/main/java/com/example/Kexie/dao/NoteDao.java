package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface NoteDao extends BaseMapper<Note> {
    @Delete("delete from sentence where word_id = #{word_id} ")
    void deleteWord(Integer wordId);
}
