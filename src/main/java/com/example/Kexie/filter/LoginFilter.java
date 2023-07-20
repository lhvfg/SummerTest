package com.example.Kexie.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("拦截请求");
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        String origin = httpRequest.getHeader("Origin");
        httpResponse.setHeader("Access-Control-Allow-Origin", origin == null ? "true" : origin);
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        //得到表示请求的字符串
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        System.out.println("访问的url:"+url);
        //在白名单中的url,放行访问
        if (url.startsWith("/static") || url.equals("/index") || url.startsWith("/login")) {
            chain.doFilter(httpRequest, httpResponse);
        }
        else {
            HttpSession session = httpRequest.getSession(false);
            if (session == null) {
                //得到session中“userId”属性，以便接下来判断是否已登录
                System.out.println("session为null");
            } else {
                System.out.println("过滤器中的session地址是" + session);
                Object userId = session.getAttribute("userId");
                System.out.println("过滤器中session中的userId:" + userId);
                //其他的请求都需要判断是否登陆，如果登录，接下来该干嘛就干嘛
                if (userId != null) {
                    System.out.println("userId不为空");
                    //若为登录状态 放行访问
                    chain.doFilter(httpRequest, httpResponse);
                }
                //如果没登录，就重定向为 /index 请求
                else {
                    //否则把请求重定向为 /index，在loginController中，这个请求会返回登录页面
                    System.out.println("还未登陆");
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                }
            }
        }
}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
