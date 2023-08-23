package com.example.Kexie.domain.Result;

import com.example.Kexie.domain.DashBoardData;
import lombok.Data;

@Data
public class DashBoardResult {
    String status;
    DashBoardData dashBoardData;
}
