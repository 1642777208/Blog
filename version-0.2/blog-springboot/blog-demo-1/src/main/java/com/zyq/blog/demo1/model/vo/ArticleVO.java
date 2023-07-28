package com.zyq.blog.demo1.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.zyq.blog.demo1.model.entity.ArticlePO;
import com.zyq.blog.demo1.model.enums.ArticleTypeEnum;
import com.zyq.blog.demo1.utils.BlogUtils;
import lombok.Data;

@Data
public class ArticleVO {
    private String id;
    private String author;
    private String title;
    private String content;
    private String[] tags;
    private String type;
    private String category;
    private String gmtCreate;
    private String gmtUpdate;
    private String tabloid;
    private Integer views;
    private String thumbnail;

    /**
     * 根据 PO 创建 VO 对象
     * @param articlePO PO对象
     * @return VO对象
     */
    public static ArticleVO fromArticlePO(ArticlePO articlePO) {
        return new Converter().convertToVO(articlePO);
    }

    private static class Converter implements ConverterInter<ArticlePO, ArticleVO> {
        @Override
        public ArticleVO convertToVO(ArticlePO article) {
            final ArticleVO vo = new ArticleVO();
            // 使用hutool从po直接向vo注入相同属性设置忽略空和报错
            BeanUtil.copyProperties(article, vo, CopyOptions.create()
                    .ignoreNullValue().ignoreError());
            // 单独设置不同属性
            vo.setTags(article.getTags().split(","));
            for (ArticleTypeEnum item : ArticleTypeEnum.values()) {
                if (item.getFlag() == article.getType()) {
                    vo.setType(item.getNotes());
                }
            }
            vo.setGmtCreate(BlogUtils.formatDatetime(article.getGmtCreate()));
            vo.setGmtUpdate(BlogUtils.formatDatetime(article.getGmtUpdate()));
            return vo;
        }
    }
}
