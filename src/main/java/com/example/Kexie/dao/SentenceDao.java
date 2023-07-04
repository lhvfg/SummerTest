package com.example.Kexie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kexie.domain.Sentence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SentenceDao extends BaseMapper<Sentence> {
}
