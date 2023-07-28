package com.zyq.blog.demo1.controller;

import com.zyq.blog.demo1.exception.BlogException;
import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.enums.ErrorInfoEnum;
import com.zyq.blog.demo1.model.vo.BlogInfoVO;
import com.zyq.blog.demo1.service.CommService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("通用接口")
@RestController
public class CommController {
    @Autowired
    CommService commService;

    @ApiOperation("检查服务端是否正常")
    @GetMapping("/ping")
    public BaseResults ping() {
//        throw new BlogException(ErrorInfoEnum.INCOMPLETE_PARAMETERS);
        return BaseResults.ok("欢迎访问IKUNBlog API", null);
    }

    @ApiOperation("获取博客信息")
    @GetMapping("/info")
    public BaseResults<BlogInfoVO> info() {
        BlogInfoVO blogInfo = commService.getBlogInfo();
        return BaseResults.ok(blogInfo);
    }



}
