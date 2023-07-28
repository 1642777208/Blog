package com.zyq.blog.demo1.controller;

import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.vo.ArticleVO;
import com.zyq.blog.demo1.model.vo.CategoryVO;
import com.zyq.blog.demo1.model.vo.PageVO;
import com.zyq.blog.demo1.service.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取所有的文章分类以及对应分类文章数")
    @GetMapping("/categories")
    public BaseResults<List<CategoryVO>> getCategories() {
        return BaseResults.ok(categoryService.getAllCategories());
    }

    @GetMapping("/category/{name}")
    @ApiOperation("根据分类查询文章集合")
    @ApiImplicitParam(name = "name", value = "分类名称", required = true, dataType = "String", paramType = "path")
    public BaseResults<PageVO<ArticleVO>> getArticle(
            @PathVariable("name") String categoryName,
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        PageVO<ArticleVO> pv = categoryService.getArticleByCategory(categoryName, page, limit);
        return BaseResults.ok(pv);
    }
}