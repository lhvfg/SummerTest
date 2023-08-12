package com.example.Kexie.domain.Result;

import lombok.Data;

@Data
public class ClockInResult {
    String status;
    Integer accumulateDay;
    Integer successiveDay;

    public ClockInResult() {
    }

    public ClockInResult(String status, Integer accumulateDay) {
        this.status = status;
        this.accumulateDay = accumulateDay;
    }
}
