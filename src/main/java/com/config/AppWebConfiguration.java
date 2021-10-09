package com.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * mvc扩展配置
 * 设置虚拟路径 设置不拦截的路径
 */
@Configuration
public class AppWebConfiguration implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截的路径
//        String[] arr = {"/LoginController/qryRefundOpInfo","/LoginController/querySecurityDatabaseInfo"};
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(arr);
    }

    /**
     * 对文件的路径进行配置, 创建一个虚拟路径/Path/** ，即只要在<img src="/Path/picName.jpg" />便可以直接引用图片
     * 这是图片的物理路径  "file:/+本地图片的地址"
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("login");
//
//        registry.addViewController("/notLogin").setViewName("notLogin");
    }
}
