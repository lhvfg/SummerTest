package com.example.Kexie.domain.FrontData;

import lombok.Data;

import java.sql.Date;

@Data
public class ReviewFrontData {
  private Integer bookId;
  private Integer userId;
  private Integer wordId;
  private Integer stage;
  private String requestType;
  private String today;
  private String nextTime;
}
