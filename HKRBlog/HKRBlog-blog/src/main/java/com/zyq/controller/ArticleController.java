package com.zyq.controller;

import com.zyq.domain.vo.BaseResult;
import com.zyq.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/test")
    public BaseResult test(){
        return BaseResult.success(articleService.list());
    }
}
