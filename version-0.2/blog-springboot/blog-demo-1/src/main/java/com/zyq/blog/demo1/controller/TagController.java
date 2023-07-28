package com.zyq.blog.demo1.controller;

import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.vo.ArticleVO;
import com.zyq.blog.demo1.model.vo.PageVO;
import com.zyq.blog.demo1.model.vo.TagVO;
import com.zyq.blog.demo1.service.TagService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation("获取所有的标签")
    @GetMapping("/tags")
    public BaseResults<Set<String>> getTags() {
        return BaseResults.ok(tagService.getAllTags());
    }

    @GetMapping("/tag/{tag}")
    @ApiOperation("根据标签查询文章集合")
    @ApiImplicitParam(name = "tag", value = "标签名称", required = true, dataType = "String", paramType = "path")
    public BaseResults<PageVO<ArticleVO>> getArticle(
            @PathVariable("tag") String tagName,
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        PageVO<ArticleVO> pv = tagService.getArticleByTag(tagName, page, limit);
        return BaseResults.ok(pv);
    }

    @GetMapping("/tags/count")
    @ApiOperation("获取所有的文章标签以及对应标签文章数")
    public BaseResults<List<TagVO>> getCountByTags() {
        List<TagVO> countByTags = tagService.countByTags();
        return BaseResults.ok(countByTags);
    }
}
