package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface WordDao extends BaseMapper<Word> {
    //用id获取拼写
    @Select("SELECT spell FROM word where id=#{id}")
    String getSpell(Integer id);
    //用拼写获取id
    @Select("SELECT id FROM word where spell=#{spell}")
    Integer getWordId(String spell);
    //获取某用户在某本单词书中没背过的单词无所谓生词
    @Select("SELECT * FROM word WHERE (id IN (SELECT word_id from book_word where book_id=#{bookId} and word_id not in (SELECT word_id from word_user where user_id = #{userId} and count != 0)))")
    List<Word> selectUnRecite(Integer bookId,Integer userId);
    //获取某用户在某本单词书中没背过的单词但又不是生词
    @Select("SELECT * FROM word WHERE (id IN (SELECT word_id from book_word where book_id=#{bookId} and word_id not in (SELECT word_id from word_user where user_id = #{userId} and count != 0)) and id not in (select word_id from star_book where user_id = #{userId}))")
    List<Word> selectNewWords(Integer bookId,Integer userId);
    //查询某用户在某本书中背过特定次数的单词无所谓生词
    @Select("SELECT * FROM word WHERE id IN (SELECT wu.word_id FROM word_user wu WHERE wu.count = #{count} AND wu.user_id = #{userId} AND wu.finish = 0 AND wu.recite = 0 AND wu.word_id IN (SELECT bw.word_id FROM book_word bw WHERE bw.book_id = #{bookId}))")
    List<Word> selectLearning(Integer count,Integer userId,Integer bookId);
    //查询某用户在某本书中背过特定次数的单词但不是生词
    @Select("SELECT * FROM word WHERE id IN (SELECT wu.word_id FROM word_user wu WHERE wu.count = #{count} AND wu.user_id = #{userId} AND wu.finish = 0 AND wu.recite = 0 AND wu.word_id IN (SELECT bw.word_id FROM book_word bw WHERE bw.book_id = #{bookId}))and id not in (select word_id from star_book where user_id = #{userId})")
    List<Word> selectCountWords(Integer count,Integer userId,Integer bookId);
    //在生词本中的新单词
    @Select("SELECT * FROM word WHERE id IN(SELECT word_id from star_book where user_id=#{userId} and (word_id not in (select word_id from word_user where user_id = #{userId} and (count != 0 or recite = 1 or finish = 1)) ))")
    List<Word> selectNewStarWords(Integer userId,Integer bookId);
    //在生词本中背过特定次数的单词
    @Select("SELECT * FROM word WHERE id IN" +
            "(SELECT wu.word_id FROM word_user wu WHERE wu.count = #{count} AND wu.user_id = #{userId} AND wu.finish = 0 AND wu.recite = 0 AND wu.word_id IN" +
            "(SELECT sb.word_id FROM star_book sb WHERE sb.user_id = #{userId}))")
    List<Word> selectCountStarWords(Integer count,Integer userId);
    //获取要复习的单词书词但不是标星词
    @Select("select * from word where id in (select word_id from word_user where recite = 1 and finish = 0 and user_id = #{userId} and (next_review is null or next_review <=#{today})) and id in (select word_id from book_word where book_id = #{bookId}) and id not in (select word_id from star_book where user_id = #{userId})")
    List<Word> selectReviewBookWords(Integer userId,Integer bookId,String today);
    //获取要复习的标星词
    @Select("select * from word where id in (select word_id from word_user where recite = 1 and finish = 0 and user_id = #{userId} and (next_review is null or next_review <=#{today})) and id in (select word_id from star_book where user_id = #{userId})")
    List<Word> selectReviewStarWords(Integer userId,String today);
    //查询要今日复习的单词数量
    @Select("select count(*) from word where id in (select word_id from word_user where recite = 1 and finish = 0 and user_id = #{userId} and (next_review is null or next_review <=#{today})) and (id in (select word_id from book_word where book_id = #{bookId}) or id in (select word_id from star_book where user_id = #{userId}))")
    Integer getReviewNum(Integer bookId, Integer userId, Date today);
    //查询生词本与单词书内背过或标熟的单词数量
    @Select("select count(*) from word where id in (select word_id from word_user where (recite = 1 or finish = 1) and user_id = #{userId}) and (id in (select word_id from book_word where book_id = #{bookId}) or id in (select word_id from star_book where user_id = #{userId}))")
    Integer getRecitedNum(Integer bookId, Integer userId);
    //获取某用户在这本书中正在复习的单词
    @Select("select * from word where id in (select word_id from word_user where finish != 1 and recite =1 and (stage <=4 or stage is null) and user_id = #{userId}) and id in (select word_id from book_word where book_id = #{bookId})")
    List<Word> selectRecitedWord(Integer userId,Integer bookId);
    //获取某用户在某本书中复习完的单词
    @Select("select * from word where id in (select word_id from word_user where (finish = 1 or stage = 5) and user_id = #{userId})and id in (select word_id from book_word where book_id = #{bookId})")
    List<Word> selectFinishedWord(Integer userId,Integer bookId);
    //获取所有在复习的单词
    @Select("select * from word where id in (select word_id from word_user where finish != 1 and recite =1 and (stage <=4 or stage is null) and user_id = #{userId})")
    List<Word> selectAllRecitedWord(Integer userId);
    //获取所有复习完成的单词
    @Select("select * from word where id in (select word_id from word_user where (finish = 1 or stage = 5) and user_id = #{userId})")
    List<Word> selectAllFinishedWord(Integer userId);
}
