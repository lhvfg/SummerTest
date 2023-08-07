package com.example.Kexie.domain;

import lombok.Data;

@Data
public class ReviewFrontData {
  private Integer bookId;
  private Integer userId;
  private Integer wordId;
  private String requestType;
  private String today;
}
