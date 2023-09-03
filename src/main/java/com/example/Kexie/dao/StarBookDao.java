package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.StarBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StarBookDao extends BaseMapper<StarBook> {
    @Select("SELECT count(*) from star_book where word_id in (select word_id from star_book where user_id = #{userId})  and word_id not in (SELECT word_id from word_user where (recite = 1 or finish = 1) and user_id = #{userId}) and word_id not in (select word_id from book_word where book_id = #{bookId})")
    public Integer getLearnStarNum(Integer bookId,Integer userId);
}
