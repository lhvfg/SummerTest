package com.example.Kexie.domain;

import com.example.Kexie.domain.BasicPojo.Meaning;
import lombok.Data;

import java.util.List;

@Data
public class Derive {
  private String spell;
  private List<Meaning> meanings;

  public Derive() {
  }

  public Derive(String spell, List<Meaning> meanings) {
    this.spell = spell;
    this.meanings = meanings;
  }
}
