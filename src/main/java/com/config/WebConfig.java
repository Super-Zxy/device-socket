package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zxy
 * @date 2020/10/27 17:08
 * @description
 */
@Configuration
public class WebConfig {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("${securityFilter.rediectErrorUrl}")
    private String rediectErrorUrl;

    //CRM嵌入时设置带入菜单URL
//    @Bean
//    public FilterRegistrationBean setMenuUrlFilter(){
//        //创建 过滤器注册bean
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//
//        SecurityFilter securityFilter = new SecurityFilter();
//
//        registrationBean.setFilter(securityFilter);
//        HashMap<String,String> initParameterMap=new HashMap<>();
//
//        initParameterMap.put("rediectErrorUrl",this.rediectErrorUrl);
//        registrationBean.setInitParameters(initParameterMap);
//
//        logger.info("加载过滤器securityFilter----------------------------");
//        List urls = new ArrayList();
//        urls.add("/*");   //给所有请求加过滤器
//        //设置 有效url
//        registrationBean.setUrlPatterns(urls);
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
}
