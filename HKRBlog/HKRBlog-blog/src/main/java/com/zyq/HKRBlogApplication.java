package com.zyq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyq.**.mapper")
public class HKRBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(HKRBlogApplication.class, args);
        System.out.println("http://localhost:8080/api/doc.html");
    }
}
