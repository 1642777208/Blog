package com.zyq.blog.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyq.blog.demo1.model.entity.ArticlePO;
import com.zyq.blog.demo1.model.vo.TagVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO> {
    // tag种类
    Integer countAllTags();
    // tag各种类数量
    List<TagVO> countByTags();
}
