//package com.example.Kexie.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MultipartResolver {
//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("UTF-8"); // 设置默认编码
//        // 根据需要进行适当的配置，例如最大上传文件大小、临时文件存储路径等
//        resolver.setMaxUploadSizePerFile(10 * 1024 * 1024); // 设置每个文件的最大上传大小为10MB
//        // resolver.setUploadTempDir(new FileSystemResource("/tmp")); // 设置临时文件存储路径
//        return resolver;
//    }
//}
