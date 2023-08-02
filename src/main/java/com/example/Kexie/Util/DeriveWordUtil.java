package com.example.Kexie.Util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Kexie.dao.MeaningDao;
import com.example.Kexie.dao.WordDao;
import com.example.Kexie.domain.BasicPojo.Meaning;
import com.example.Kexie.domain.BasicPojo.Word;
import com.example.Kexie.domain.Derive;
import com.example.Kexie.domain.ReciteWordData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class DeriveWordUtil {
    //获取派生词,参数是拼写和要处理后返回的ReciteWordDate
    public ReciteWordData getDerive(MeaningDao meaningDao, WordDao wordDao, ReciteWordData reciteWordDate, String spell){
        //获取派生词，头尾不断截取字母，直达获取了4个及以上的形近词
        //不截取
        List<Word> derviedWords = wordDao.selectList(new LambdaQueryWrapper<Word>().like(Word::getSpell, spell));
        String copySpell = spell;
        while(derviedWords.size()<4&&copySpell.length()>1)
        {
//            String frontHandledSpell = copySpell.substring(1); //首个字符截去
//            List<Word> frontAdd = wordDao.selectList(new LambdaQueryWrapper<Word>().like(Word::getSpell, frontHandledSpell));
//            if (frontAdd.size()!=0)
//            {
//                frontAdd.forEach(word -> {
//                    if (!derviedWords.contains(word)&&derviedWords.size()<8) {
//                        derviedWords.add(word);
//                    }
//                });
//            }
            String endHandledSpell = copySpell.substring(0,copySpell.length()-1); //末尾字符截去
            List<Word> endAdd = wordDao.selectList(new LambdaQueryWrapper<Word>().like(Word::getSpell, endHandledSpell));
            if (endAdd.size()!=0)
            {
                endAdd.forEach(word -> {
                    if (derviedWords.contains(word)&&derviedWords.size()<6)
                        derviedWords.add(word);
                });
            }
            //copySpell收尾截去一个字符迭代
            copySpell = copySpell.substring(0,copySpell.length()-1);
        }
        //这里包含了自己的拼写
        Derive[] derives = new Derive[derviedWords.size()-1];
        if (!derviedWords.isEmpty())
        {
            final int[] i = {0};
            derviedWords.forEach(word -> {
                //除了自己以外的单词
                if(!word.getSpell().equals(spell))
                {
                    List<Meaning> meanings = meaningDao.selectList(new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId, word.getId()));
                    derives[i[0]] = new Derive(word.getSpell(), meanings);
                    i[0]++;
                }
            });
        }
        reciteWordDate.setDerived(derives);
        return reciteWordDate;
    }
}
