package com.zyq.blog.demo1.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.zyq.blog.demo1.model.comm.BlogSetting;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class BlogInfoVO {
    private String title;
    private String avatar;
    private List<String> desc;
    private String cover;
    private int articleCount;
    private int totalCategory;
    private int totalTag;

    public static BlogInfoVO fromBlogSetting(BlogSetting blogSetting) {
        return new BlogInfoVO.Converter().convertToVO(blogSetting);
    }

    private static class Converter implements ConverterInter<BlogSetting, BlogInfoVO> {
        @Override
        public BlogInfoVO convertToVO(BlogSetting blogSetting) {
            BlogInfoVO vo = new BlogInfoVO();
            BeanUtil.copyProperties(blogSetting, vo);
/*            // 分割
            vo.setDesc(Arrays.stream(blogSetting.getDesc().split(";"))
                    .collect(Collectors.toList()));*/
            // 随机从图片里拿一个
            int index = (int) (Math.random()* blogSetting.getCovers().size());
            vo.setCover(blogSetting.getCovers().get(index));
            return vo;
        }
    }
}
