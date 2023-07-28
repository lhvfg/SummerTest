package com.example.Kexie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.Result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public Result addWordRequest(@RequestBody WordData wordDate) {
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
                if (noteContent[i]!=null)
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
    @PostMapping("/upload")
    //public Result upload(@RequestBody FileData fileData) throws IOException {
    public Result upload(@RequestPart("file") MultipartFile file, @RequestParam("bookId[]") Integer[] bookIds) throws IOException {
        Result result = new Result();
        //InputStream inputStream = fileData.getFile().getInputStream();
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Integer wordId = null;
        String line = reader.readLine();
        while (line!=null)
        {
            for (int i = 0; i < 4 && line != null; i++, line = reader.readLine()) {
                System.out.println(line);
                if (i == 0) {
                    System.out.println("111" + line);
                    Word word = new Word(line);
                    wordId = wordDao.selectWordId(line);
                    if(wordId == null)
                    {
                        wordDao.insert(word);
                        wordId = wordDao.selectWordId(line);
                    }
                    for (Integer bookId : bookIds) {
                        //如果这本书里没有
                        if (book_wordDao.selectOne(new LambdaQueryWrapper<Book_word>().eq(Book_word::getWord_id,wordId).eq(Book_word::getBook_id,bookId)) == null)
                        {
                            book_wordDao.insert(new Book_word(wordId,bookId));
                        }
                    }
                } else if (i == 1) {
                    String[] mean = line.split("。");
                    for (String m : mean) {
                        String[] detail = m.split(" ");
                        System.out.println(detail[0]);
                        String function = detail[0];
                        System.out.println(detail[1]);
                        String content = detail[1];
                        Meaning meaning = new Meaning(wordId, content, function);
                        LambdaQueryWrapper<Meaning> lqw = new LambdaQueryWrapper<Meaning>().eq(Meaning::getWordId,wordId).eq(Meaning::getFunction,function);
                        if(meaningDao.selectOne(lqw)==null)
                        meaningDao.insert(meaning);
                        else{
                            meaningDao.update(meaning,lqw);
                        }
                    }
                } else if (i == 2) {
                    String sentence = line;
                    line = reader.readLine();i++;
                    String sentenceMean = line;
                    Sentence s = new Sentence(wordId, sentence, sentenceMean);
                    LambdaQueryWrapper<Sentence> lqw = new LambdaQueryWrapper<Sentence>().eq(Sentence::getWordId,wordId).eq(Sentence::getContent,sentence);
                    if(sentenceDao.selectOne(lqw)==null)
                        sentenceDao.insert(s);
                    else
                        sentenceDao.update(s,lqw);
                }
            }
        }
        reader.close();
        result.setStatus("uploadSuccess");
        return result;
    }
}

