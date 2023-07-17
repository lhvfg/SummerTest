package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WordDao extends BaseMapper<Word> {
    @Select("SELECT spell,w.id FROM word w LEFT JOIN word_user wu ON w.id = wu.word_id AND wu.user_id = #{id} WHERE wu.id IS NULL")
    List<Word> selectNewWords(Integer id);
    @Select("SELECT spell FROM word where id=#{id}")
    String selectReciteWordSpell(Integer id);
    @Select("SELECT * FROM word WHERE id IN (SELECT word_id FROM word_user WHERE count = #{count} AND user_id = #{id} AND finish = 0 AND recite = 0)")
    List<Word> selectCountWords(Integer count,Integer id);
}
