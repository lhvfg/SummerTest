package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookDao3 extends BaseMapper<Book>  {
//    @Select("select * from book where book_id = #{id}")
//    public Book find1(int id);
}
