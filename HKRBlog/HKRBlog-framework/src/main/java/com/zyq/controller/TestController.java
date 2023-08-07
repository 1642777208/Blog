package com.zyq.controller;

import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.exception.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
//    @Autowired
//    PasswordEncoder passwordEncoder;
    @GetMapping("/ping")
    public BaseResult ping(){
        return BaseResult.success();
    }

    @GetMapping("/exception")
    public BaseResult exception(){
        throw new BlogException(BlogErrorInfoEnum.SUCCESS);
    }

    @GetMapping("/authenticated")
    public BaseResult authenticated(){
        return BaseResult.success("登录成功");
    }
}
