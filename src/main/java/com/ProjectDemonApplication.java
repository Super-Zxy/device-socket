package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//会自动读取spring.datasource.*属性并自动配置单数据源 配置多数据源要禁用
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
@EnableAsync
public class ProjectDemonApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProjectDemonApplication.class, args);

    }
}
