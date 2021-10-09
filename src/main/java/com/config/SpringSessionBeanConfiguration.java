//package com.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.session.web.http.CookieHttpSessionIdResolver;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;
//import org.springframework.session.web.http.HttpSessionIdResolver;
//
///**
// * @author zxy
// * @date 2021/5/17 23:46
// * @description
// */
//@Configuration
//public class SpringSessionBeanConfiguration {
//
//
//    //Cookie配置
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        //iframe 嵌入也能带入cookie
//        cookieSerializer.setSameSite(null);
//        return cookieSerializer;
//    }
//
//    //HttpSessionId配置
//    @Bean
//    public HttpSessionIdResolver httpSessionIdResolver() {
//        CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
//        cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer());
//        return cookieHttpSessionIdResolver;
//    }
//
//}