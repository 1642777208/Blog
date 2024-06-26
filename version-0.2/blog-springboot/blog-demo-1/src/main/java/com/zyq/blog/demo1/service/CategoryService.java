package com.zyq.blog.demo1.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.blog.demo1.mapper.ArticleMapper;
import com.zyq.blog.demo1.model.entity.ArticlePO;
import com.zyq.blog.demo1.model.vo.ArticleVO;
import com.zyq.blog.demo1.model.vo.CategoryVO;
import com.zyq.blog.demo1.model.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {
    @Autowired
    private ArticleMapper articleMapper;

    public List<CategoryVO> getAllCategories() {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("category as `name`", "count(1) as `count`").groupBy("category");
        List<CategoryVO> list = articleMapper.selectMaps(wrapper).stream()
                .map(CategoryVO::fromMap)
                .collect(Collectors.toList());
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    public PageVO<ArticleVO> getArticleByCategory(String categoryName, Integer page, Integer limit) {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select(ArticlePO.class, i -> !"content".equals(i.getColumn()))
                .eq("category", categoryName);
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
}

