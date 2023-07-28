package com.zyq.blog.demo1.service;

import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyq.blog.demo1.mapper.ArticleMapper;
import com.zyq.blog.demo1.model.comm.BlogSetting;
import com.zyq.blog.demo1.model.entity.ArticlePO;
import com.zyq.blog.demo1.model.vo.BlogInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private Setting setting;

    public BlogInfoVO getBlogInfo() {
        BlogSetting blogSetting = BlogSetting.fromSetting(setting);
        BlogInfoVO vo = BlogInfoVO.fromBlogSetting(blogSetting);
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT category");
        Integer totalCategory = articleMapper.selectCount(wrapper);
        Integer articleCount = articleMapper.selectCount(null);
        Integer totalTag = articleMapper.countAllTags();
        vo.setTotalTag(totalTag);
        vo.setTotalCategory(totalCategory);
        vo.setArticleCount(articleCount);
        return vo;
    }
}
