package com.example.Kexie;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Kexie.dao.*;
import com.example.Kexie.domain.BasicPojo.*;
import com.example.Kexie.domain.Result.Result;

import com.example.Kexie.domain.WordData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.sql.Date;
import java.sql.Time;

@SpringBootTest
@RestController
class DemoApplicationTests {
    @Autowired
    private Word_userDao word_userDao;

    @Test
    private void l(){
//        try {
//            //创建工作簿对象
//            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("E://java17//java_practise//poiReadExcel//src//main//resources//chart.xlsx"));
//            //获取工作簿下sheet的个数
//            int sheetNum = xssfWorkbook.getNumberOfSheets();
//            System.out.println("该excel文件中总共有："+sheetNum+"个sheet");
//            //遍历工作簿中的所有数据
//            for(int i = 0;i<sheetNum;i++) {
//                //读取第i个工作表
//                System.out.println("读取第"+(i+1)+"个sheet");
//                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
//                //获取最后一行的num，即总行数。此处从0开始
//                int maxRow = sheet.getLastRowNum();
//                for (int row = 0; row <= maxRow; row++) {
//                    //获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
//                    int maxRol = sheet.getRow(row).getLastCellNum();
//                    System.out.println("--------第" + row + "行的数据如下--------");
//                    for (int rol = 0; rol < maxRol; rol++){
//                        System.out.print(sheet.getRow(row).getCell(rol) + "  ");
//                    }
//                    System.out.println();
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
