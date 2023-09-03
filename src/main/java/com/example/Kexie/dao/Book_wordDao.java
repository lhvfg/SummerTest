package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.Book_word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Book_wordDao extends BaseMapper<Book_word> {
    // 查询要背的单词数量
    @Select("SELECT count(*) from book_word where book_id = #{bookId} and word_id not in (SELECT word_id from word_user where (recite = 1 or finish = 1) and user_id = #{userId});")
    Integer getBookLearnNum(Integer bookId,Integer userId);

}
