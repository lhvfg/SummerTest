package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.BasicPojo.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Time;

@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("select * from user where id = #{id}")
    User findById(Integer id);
    @Update("UPDATE user set today_num = today_num + #{num},all_num = all_num +#{num},today_time = SEC_TO_TIME(TIME_TO_SEC(today_time)+ #{second}),all_time = SEC_TO_TIME(TIME_TO_SEC(all_time)+ #{second}) WHERE id = #{id}")
    Boolean changeNumTime(Integer num , Integer second , Integer id);
}
