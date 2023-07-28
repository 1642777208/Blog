package com.zyq.blog.demo1.model.comm;

import cn.hutool.setting.Setting;
import lombok.Data;

import java.util.List;

@Data
public class BlogSetting {
    private String title;
    private String avatar;
    private List<String> desc;
    private List<String> covers;

    public static BlogSetting fromSetting(Setting setting){
        return (BlogSetting) setting.toBean(new BlogSetting());
    }
}