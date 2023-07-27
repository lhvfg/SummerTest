package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.Word_user;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface Word_userDao extends BaseMapper<Word_user> {
    //次数加一
    @Update("UPDATE word_user set count = count+1 where word_id = #{wordId} AND user_id = #{userId)")
    boolean countAdd(Integer wordId,Integer userId);
    //次数清空
    @Update("UPDATE word_user set count = 0 where word_id = #{wordId} AND user_id = #{userId)")
    boolean countClear(Integer wordId,Integer userId);
    //背完一个
    @Update("UPDATE word_user set count = 3 , recite = 1 where word_id = #{wordId} AND user_id = #{userId)")
    boolean wordRecite(Integer wordId,Integer userId);
    //标熟
    @Update("UPDATE word_user set finish = 1 where word_id = #{wordId} and user_id = #{userId}")
    boolean deleteWord(Integer wordId,Integer userId);
    //标熟生词，会添加一条数据
    @Update("INSERT INTO word_user (recite,count, finish, user_id, word_id) VALUES (0,0,1,#{userId},#{wordId});")
    boolean deleteNewWord(Integer wordId,Integer userId);
    //取消标熟
    @Update("UPDATE word_user set finish = 0 where word_id = #{wordId} and user_id = #{userId}")
    boolean undoDelete(Integer wordId,Integer userId);
    //删除背诵记录，防止出现conut = 0,finish = 0,recite = 0的数据
    @Delete("DELETE from word_user where word_id = #{wordId} and user_id = #{userId}")
    boolean trueDelete(Integer wordId,Integer userId);
    //插入记录，防止出现单词次数清空后设置标熟又取消后丢失数据，无法更新count数据
    @Insert("INSERT INTO word_user (recite, count, finish, user_id, word_id) VALUES (0,1,0,#{userId},#{wordId})")
    boolean insertData(Integer userId,Integer wordId);
}
