package com.zyq.blog.demo1.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.blog.demo1.mapper.ArticleMapper;
import com.zyq.blog.demo1.model.entity.ArticlePO;
import com.zyq.blog.demo1.model.vo.ArticleVO;
import com.zyq.blog.demo1.model.vo.PageVO;
import com.zyq.blog.demo1.model.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagService {
    @Autowired
    private ArticleMapper articleMapper;

    /***
     * 查询所有标签
     * @return 标签集合
     */
    public Set<String> getAllTags() {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("tags");
        List<ArticlePO> articles = articleMapper.selectList(wrapper);
        /*
        *   Set 用于去重
        *   flatMap 方法可以将多个流合并成一个流
        * */
        Set<String> tags = articles.stream()
                .map(ArticlePO::getTags)
                .flatMap(s -> Arrays.stream(s.split(",")))
                .collect(Collectors.toSet());
//        log.info("tags : {}", tags);
        return tags.isEmpty() ? new HashSet<>() : tags;
    }
    /***
     * 查询所有标签
     * @return 该标签所有文章
     */
    public PageVO<ArticleVO> getArticleByTag(String tagName, Integer page, Integer limit) {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select(ArticlePO.class, i -> !"content".equals(i.getColumn()))
                .like("tags", tagName);
        Page<ArticlePO> res = articleMapper.selectPage(new Page<>(page, limit), wrapper);
        List<ArticleVO> articleVOS = res.getRecords().stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        PageVO<ArticleVO> pv = PageVO.<ArticleVO>builder()
                .records(articleVOS.isEmpty() ? new ArrayList<>() : articleVOS)
                .total(res.getTotal())
                .current(res.getCurrent())
                .size(res.getSize())
                .build();
        return pv;
    }
    /***
     * 查询所有标签数量
     * @return 有该标签的文章数量
     */

    public List<TagVO> countByTags(){
        return articleMapper.countByTags();
    }
}

