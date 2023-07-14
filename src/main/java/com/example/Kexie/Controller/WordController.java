package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class WordController {
    @Autowired
    WordDao wordDao;
    @Autowired
    NoteDao noteDao;
    @Autowired
    SentenceDao sentenceDao;
    @Autowired
    MeaningDao meaningDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    Book_wordDao book_wordDao;
    @Autowired
    Word_userDao word_userDao;

    @PostMapping("/addword")//添加单词
    public Result addWordRequest(@RequestBody WordDate wordDate) {
        Result result = new Result();
        QueryWrapper<Word> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Word> lqw = new LambdaQueryWrapper<>();
        // 判断单词是否存在
        if (wordDate.getRequestType().equals("addWordRequest")) {
            lqw.eq(Word::getSpell, wordDate.getSpell());
            Word word = wordDao.selectOne(lqw);
            if (word != null) {
                result.setStatus("wordExisted");
                result.setWordId(word.getId());
            } else {
                qw.select("max(id) as id");
                result.setStatus("wordNotExist");
                result.setWordId(wordDao.selectOne(qw).getId() + 1);
                System.out.println(wordDao.selectOne(qw).getId());
            }
        }
        //添加单词
        else if (wordDate.getRequestType().equals("addWord")) {
            Integer wordId = wordDate.getWordId();
            Integer userId = wordDate.getUserId();
            Integer[] bookId = wordDate.getBookId();

            String[] noteContent = wordDate.getNoteContent();
            String[] sentenceContent = wordDate.getSentenceContent();
            String[] sentenceContentMean = wordDate.getSentenceContentMean();
            String[] meaningContent = wordDate.getMeaningContent();
            String[] function = wordDate.getFunction();

            Word word = new Word(wordId, wordDate.getSpell());
            //新增单词
            if (wordDate.getWordType() == 1) {
                wordDao.insert(word);
                // 添加至指定单词书
                for (int i = 0; i < bookId.length; i++) {
                    bookDao.addWord(bookId[i]);
                    Book_word book_word = new Book_word(wordId, bookId[i]);
                    book_wordDao.insert(book_word);
                }
            }
            //修改单词
            else {
                sentenceDao.deleteWord(wordId);
                meaningDao.deleteWord(wordId);
                noteDao.deleteWord(wordId);
            }
            //添加内容
            for (int i = 0; i < noteContent.length; i++) {
                Note note = new Note();
                note.setWordId(wordId);
                note.setContent(noteContent[i]);
                note.setUserId(userId);
                noteDao.insert(note);
            }
            for (int i = 0; i < sentenceContent.length; i++) {
                Sentence sentence = new Sentence();
                sentence.setWordId(wordId);
                sentence.setContent(sentenceContent[i]);
                sentence.setContentMean(sentenceContentMean[i]);
                sentenceDao.insert(sentence);
            }
            for (int i = 0; i < meaningContent.length; i++) {
                Meaning meaning = new Meaning();
                meaning.setWordId(wordId);
                meaning.setContent(meaningContent[i]);
                meaning.setFunction(function[i]);
                meaningDao.insert(meaning);
            }
            result.setStatus("addWordSucceed");
        }
        //获取单词书列表，不用分页
        else if (wordDate.getRequestType().equals("bookList")) {
            List<Book> books = bookDao.selectList(null);
            result.setBookList(books);
            result.setStatus("bookList");
        }
        return result;
    }

}

