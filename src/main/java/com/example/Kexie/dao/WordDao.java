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
    @Select("SELECT spell FROM word where id=#{id}")
    String selectReciteWordSpell(Integer id);
    //获取某用户在某本单词书中没背过的单词
    @Select("SELECT * FROM word WHERE id NOT IN (SELECT word_id from book_word where book_id=#{bookId} and word_id in (SELECT word_id from word_user where user_id = #{userId}) );")
    List<Word> selectNewWords(Integer bookId,Integer userId);
    //查询某用户在某本书中背过特定次数的单词
    @Select("SELECT * FROM word WHERE id IN (SELECT wu.word_id FROM word_user wu WHERE wu.count = #{count} AND wu.user_id = #{userId} AND wu.finish = 0 AND wu.recite = 0 AND wu.word_id IN (SELECT bw.word_id FROM book_word bw WHERE bw.book_id = #{bookId}))")
    List<Word> selectCountWords(Integer count,Integer userId,Integer bookId);
    //在生词本中的新单词
    @Select("SELECT * FROM word WHERE id IN(SELECT word_id from star_book  where user_id=#{userId} and word_id not in (select word_id from word_user where user_id = #{userId}))")
    List<Word> selectNewStarWords(Integer userId);
    //在生词本中背过特定次数的单词
    @Select("SELECT * FROM word WHERE id IN" +
            "(SELECT wu.word_id FROM word_user wu WHERE wu.count = #{count} AND wu.user_id = #{userId} AND wu.finish = 0 AND wu.recite = 0 AND wu.word_id IN" +
            "(SELECT sb.word_id FROM star_book sb WHERE sb.user_id = #{userId}))")
    List<Word> selectCountStarWords(Integer count,Integer userId);
}
