package com.example.Kexie.domain;

import lombok.Data;

import java.sql.Time;

@Data
public class DashBoardData {
   String bookName;
   Long starNum;
   //背过的单词
   Integer recitedNum ;
   //所有要学的单词：单词书单词书+生词本单词数-finish==1的
   Long allStudyNum ;
   Integer todayNum ;
   Long allRecitedNum ;
   Time todayTime ;
   Time allTime ;

   public DashBoardData() {
   }

   public DashBoardData(String bookName, Long starNum, Integer recitedNum, Long allStudyNum, Integer todayNum, Long allRecitedNum, Time todayTime, Time allTime) {
      this.bookName = bookName;
      this.starNum = starNum;
      this.recitedNum = recitedNum;
      this.allStudyNum = allStudyNum;
      this.todayNum = todayNum;
      this.allRecitedNum = allRecitedNum;
      this.todayTime = todayTime;
      this.allTime = allTime;
   }
}
