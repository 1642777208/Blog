package com.zyq.controller;

import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.exception.BlogException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/ping")
    public BaseResult ping(){
        return BaseResult.success();
    }

    @GetMapping("/exception")
    public BaseResult exception(){
        throw new BlogException(BlogErrorInfoEnum.SUCCESS);
    }
}
