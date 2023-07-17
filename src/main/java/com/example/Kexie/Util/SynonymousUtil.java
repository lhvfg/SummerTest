package com.example.Kexie.Util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.dao.MeaningDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.BasicPojo.Meaning;
import com.example.Kexie.domain.ReciteWordDate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
public class SynonymousUtil {
    public void getSynonymous(ReciteWordDate reciteWordDate, Meaning wordMean, MeaningDao meaningDao, WordDao wordDao){
        //用中英文逗号和分号符号分割字符串
        for (String wordMeanContent : wordMean.getContent().split("[,;，；]"))
        {
            Map<Map<String,String>, Set<String>> Synonymous = new HashMap<>();
            Set<String> spells = new TreeSet<>();
            Map<String,String> mean = new HashMap<>();
            //获取含有相同释义的所有meaning数据
            List<Meaning> meanings = meaningDao.selectList(
                    new LambdaQueryWrapper<Meaning>()
                            .eq(Meaning::getFunction, wordMean.getFunction())//词性相同
                            .like(Meaning::getContent, wordMeanContent)//模糊查询，包含相同释义
                            .ne(Meaning::getId, wordMean.getId())//不是自己
            );
            //如果存在除了自己的近义词
            if (meanings.size() > 0) {
                System.out.println("存在近义词");
                //设置前键
                mean.put(meanings.get(0).getFunction(), wordMeanContent);
                //对于每个释义相应的单词id，查询对应拼写
                meanings.forEach(m -> {
                    String spell = wordDao.selectReciteWordSpell(m.getWordId());
                    spells.add(spell);
                });
                Synonymous.put(mean, spells);
                //返回除了自己所有近义词的拼写
                reciteWordDate.setSynonymous(Synonymous);
                System.out.println(reciteWordDate);
            }
        }
//        if (!f)
//        {
//            //获取含有相同释义的所有meaning数据
//            List<Meaning> meanings = meaningDao.selectList(
//                    new LambdaQueryWrapper<Meaning>()
//                                .eq(Meaning::getFunction, wordMean.getFunction())//词性相同
//                                .like(Meaning::getContent, wordMean.getContent())//模糊查询，包含相同释义
//                                .ne(Meaning::getId, wordMean.getId()));   //不是自己
//            //如果存在除了自己的近义词
//            if (meanings.size() > 0) {
//                System.out.println("存在近义词");
//                //设置前键
//                mean.put(meanings.get(0).getFunction(), wordMean.getContent());
//                //对于每个释义相应的单词id，查询对应拼写
//                meanings.forEach(m -> {
//                    String spell = wordDao.selectReciteWordSpell(m.getWordId());
//                    spells.add(spell);
//                });
//                Synonymous.put(mean, spells);
//                //返回除了自己所有近义词的拼写
//                reciteWordDate.setSynonymous(Synonymous);
//                System.out.println(reciteWordDate);
//                }
//        }
    }
}
