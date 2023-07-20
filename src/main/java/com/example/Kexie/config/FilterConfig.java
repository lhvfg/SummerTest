//package com.example.Kexie.config;
//
//import com.example.Kexie.filter.LoginFilter;
//import jakarta.servlet.Filter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new LoginFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setName("LoginFilter");
//        return registrationBean;
//    }
//    //把自己的过滤器创建成一个bean
//    @Bean(name = "LoginFilter")
//    public Filter loginFilter () {
//        return new LoginFilter();
//    }
//
//}
