package com.zyq.blog.demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//配置扫描接口
@MapperScan("com.zyq.blog.demo1.mapper")
public class BlogDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(BlogDemo1Application.class, args);
    }

}
