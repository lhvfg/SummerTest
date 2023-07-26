package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.Word_user;
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
    @Update("UPDATE word_user set count = 0 , recite = 1 where word_id = #{wordId} AND user_id = #{userId)")
    boolean wordRecite(Integer wordId,Integer userId);
    //标熟
    @Update("UPDATE word_user set finish = 1 where word_id = #{wordId} and user_id = #{userId}")
    boolean deleteWord(Integer wordId,Integer userId);
    //取消标熟
    @Update("UPDATE word_user set finish = 0 where word_id = #{wordId} and user_id = #{userId}")
    boolean undoDelete(Integer wordId,Integer userId);
}
