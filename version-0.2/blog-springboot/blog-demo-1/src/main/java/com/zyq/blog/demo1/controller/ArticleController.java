package com.zyq.blog.demo1.controller;

import cn.hutool.core.map.MapUtil;
import com.zyq.blog.demo1.annotation.Token;
import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.dto.ArticleDTO;
import com.zyq.blog.demo1.model.vo.ArticleVO;
import com.zyq.blog.demo1.model.vo.PageVO;
import com.zyq.blog.demo1.model.vo.TimelineVO;
import com.zyq.blog.demo1.service.ArticleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api("与文章相关的api接口")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation("批量获取文章")
    @GetMapping("/articles")
    public BaseResults<PageVO> getArticles(
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        return BaseResults.ok(articleService.getArticles(page, limit));
    }

    @GetMapping("/article/{id}")
    @ApiOperation("根据id查询文章信息")
    public BaseResults<ArticleVO> getArticle(@PathVariable String id) {
        ArticleVO articleVO = articleService.findById(id);
        return BaseResults.ok(articleVO);
    }

    @Token(admin = true)
    @PostMapping("/articles")
    @ApiOperation("新增文章")
    public BaseResults postArticles(@ApiParam(name = "文章信息", value = "传入json格式", required = true)
                                    @RequestBody @Valid ArticleDTO articleDTO) {
        // @Valid：用于校验参数，校验失败的话就会抛出 MethodArgumentNotValidException 异常
        String id = articleService.insArticle(articleDTO);
        return BaseResults.ok(MapUtil.of("id", id));
    }

    @Token(admin = true)
    @DeleteMapping("/article/{id}")
    @ApiOperation("根据id删除文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public BaseResults deleteArticle(@PathVariable String id) {
        articleService.deleteArticle(id);
        return BaseResults.ok("删除成功", null);
    }

    @Token(admin = true)
    @PutMapping("/article/{id}")
    @ApiOperation("修改文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public BaseResults<Map<String, Object>> putArticles(@ApiParam(name = "要修改的文章信息", value = "传入json格式", required = true)
                                                        @RequestBody ArticleDTO articleDTO,
                                                        @PathVariable String id) {
        articleService.updateArticle(articleDTO, id);
        return BaseResults.ok("更新成功", MapUtil.of("id", id));
    }

    @GetMapping("/timeline")
    @ApiOperation("获取文章时间线")
    public BaseResults<List<TimelineVO>> getTimeline() {
        List<TimelineVO> timeline = articleService.timeline();
        return BaseResults.ok(timeline);
    }

    @GetMapping("/articles/hot/")
    @ApiOperation("获取最热文章")
    @ApiImplicitParam(name = "limit", value = "数量", required = true, dataType = "int", paramType = "path")
    public BaseResults<List<ArticleVO>> getHotArticles(@RequestParam("limit") Integer limit) {
        List<ArticleVO> hotArticle = articleService.getHotArticles(limit);
        return BaseResults.ok(hotArticle);
    }

    @GetMapping("/articles/new/")
    @ApiOperation("获取最新文章")
    @ApiImplicitParam(name = "limit", value = "数量", required = true, dataType = "int", paramType = "query")
    public BaseResults<List<ArticleVO>> getNewArticles(@RequestParam("limit") Integer limit) {
        List<ArticleVO> hotArticle = articleService.getNewArticles(limit);
        return BaseResults.ok(hotArticle);
    }

    @GetMapping("/articles/search/")
    @ApiOperation("搜索文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "分页", required = true, dataType = "int", paramType = "query")
    })
    public BaseResults<PageVO> searchArticles(@RequestParam("keyword") String str,
                                                       @RequestParam("limit") Integer limit,
                                                       @RequestParam("page") Integer page) {
        PageVO<ArticleVO> searchArticles = articleService.searchArticles(str, limit, page);
        return BaseResults.ok(searchArticles);
    }
}