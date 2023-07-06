package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookDao extends BaseMapper<Book>  {
@Update("update book set word_num = word_num+1 where id =#{id}")
    boolean addWord(Integer id);
}
