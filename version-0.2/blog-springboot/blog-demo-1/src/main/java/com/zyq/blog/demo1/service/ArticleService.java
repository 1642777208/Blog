package com.zyq.blog.demo1.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.blog.demo1.exception.BlogException;
import com.zyq.blog.demo1.mapper.ArticleMapper;
import com.zyq.blog.demo1.model.dto.ArticleDTO;
import com.zyq.blog.demo1.model.entity.ArticlePO;
import com.zyq.blog.demo1.model.enums.ErrorInfoEnum;
import com.zyq.blog.demo1.model.vo.ArticleVO;
import com.zyq.blog.demo1.model.vo.PageVO;
import com.zyq.blog.demo1.model.vo.TimelineVO;
import com.zyq.blog.demo1.utils.BlogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public PageVO<ArticleVO> getArticles(int page, int limit) {
        QueryWrapper<ArticlePO> qw = new QueryWrapper<>();
        qw.select(ArticlePO.class, i -> !"content".equals(i.getColumn()))
                .last("order by gmt_create desc");
        Page<ArticlePO> res = articleMapper.selectPage(new Page<>(page, limit), qw);
        /*
         *   java1.8新增高级操作配合lamada表达式使用
         *   从List中获取流
         *   然后一一映射通过ArticleVO::fromArticlePO转换为ArticleVO
         *   最后存入新的List
         *   https://blog.csdn.net/BHSZZY/article/details/122860048
         * */
        List<ArticleVO> articleVOS = res.getRecords().stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                // 防止没有记录的空指针错误
                .records(articleVOS.isEmpty() ? new ArrayList<>() : articleVOS)
                .total(res.getTotal())
                .current(res.getCurrent())
                .size(res.getSize())
                .build();
        return pageVO;
    }

    // 生成id并插入数据库
    public String insArticle(ArticleDTO articleDTO) {
        ArticlePO po = articleDTO.toArticlePO(false);
        String id = IdUtil.objectId();
        po.setId(id);
        articleMapper.insert(po);
        return id;
    }

    // id查找文章
    public ArticleVO findById(String id) {
        ArticlePO articlePO = articleMapper.selectById(id);
        if (Objects.isNull(articlePO)) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
        // 每查找一次浏览量加一
        articlePO.setViews(articlePO.getViews() + 1);
        articleMapper.updateById(articlePO);
        return ArticleVO.fromArticlePO(articlePO);
    }

    // id删除文章
    public void deleteArticle(String id) {
        int i = articleMapper.deleteById(id);
        if (i <= 0) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
    }

    // id更新文章
    public void updateArticle(ArticleDTO articleDTO, String id) {
        ArticlePO dbArticle = articleMapper.selectById(id);
        if (Objects.isNull(dbArticle)) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
        ArticlePO articlePO = articleDTO.toArticlePO(true);
        articlePO.setId(id);
        articleMapper.updateById(articlePO);
    }

    // 时间线按年分类
    public List<TimelineVO> timeline() {
        ArrayList<TimelineVO> res = new ArrayList<>();
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "gmt_create", "thumbnail");
        List<Map<String, Object>> maps = articleMapper.selectMaps(wrapper);
        maps.stream().map(m -> TimelineVO.Item.builder()
                .id((String) m.get("id"))
                .gmtCreate(BlogUtils.formatDatetime((Long) m.get("gmt_create")))
                .title((String) m.get("title"))
                .thumbnail((String) m.get("thumbnail"))
                .build())
                // 按年分
                .collect(Collectors.groupingBy(item -> {
                    String[] arr = item.getGmtCreate().split("-");
                    String year = arr[0];
                    return year;
                })).forEach((k, v) -> res.add(TimelineVO.builder().year(k).items(v).build()));
        res.sort(Comparator.comparing(TimelineVO::getYear).reversed());
        // log.info("timeline list : {}", JSONUtil.toJsonStr(res));
        return res;
    }

    // 查询最新文章
    public List<ArticleVO> getNewArticles(int limit) {
        QueryWrapper<ArticlePO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select(ArticlePO.class, i -> !i.getColumn().equals("content"))
                .orderByDesc("gmt_create")
                .last("limit " + limit);
        List<ArticleVO> articleVOS = articleMapper.selectList(queryWrapper).stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        return articleVOS;
    }

    // 查询最热文章
    public List<ArticleVO> getHotArticles(int limit) {
        QueryWrapper<ArticlePO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select(ArticlePO.class, i -> !i.getColumn().equals("content"))
                .orderByDesc("views")
                .last("limit " + limit);
        List<ArticleVO> articleVOS = articleMapper.selectList(queryWrapper).stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        return articleVOS;
    }

    public PageVO<ArticleVO> searchArticles(String str, Integer limit, Integer page) {
        QueryWrapper<ArticlePO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select(ArticlePO.class, i -> !i.getColumn().equals("content"))
                .like("tabloid", str)
                .or().like("tags", str)
                .or().like("category", str)
                .or().like("title", str);
        Page<ArticlePO> PagePO = articleMapper.selectPage(new Page<>(page, limit), queryWrapper);
        List<ArticleVO> voList = PagePO.getRecords().stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());

        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                .records(voList.isEmpty() ? new ArrayList<>() : voList)
                .total(PagePO.getTotal())
                .current(PagePO.getCurrent())
                .size(PagePO.getSize())
                .build();
        return pageVO;
    }
}
