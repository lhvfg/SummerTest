package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.User;

import com.example.Kexie.domain.User2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao  {
    @Select("select * from user where UserId = #{id}")
    public User find(int id);
}
