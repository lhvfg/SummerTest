package com.example.Kexie.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileData {
    private Integer[] bookId;
    private MultipartFile file;
}
