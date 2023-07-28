package com.zyq.blog.demo1.config;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import com.zyq.blog.demo1.model.comm.BlogSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Slf4j
@Configuration
public class BlogSettingConfig {

    @Autowired
    ResourceLoader resourceLoader;
    @Value("${setting.path}")
    private String settingFilePath;

    @Bean
    public Setting setting() throws IOException {
        // 通过resourceLoader解析地址获取blog.setting配置文件
//        Resource resource = resourceLoader.getResource(settingFilePath);
//        File file = resource.getFile();
        /*if (!file.exists()) {
            setting = new Setting(file, CharsetUtil.CHARSET_UTF_8, false);
            setting.set("title", "竹林客栈");
            setting.set("desc", "不求风度翩翩，但求风骨立世；没有书生意气，也要为义为仁。");
            setting.set("covers", "https://tvax1.sinaimg.cn/mw1024/bfe05ea9ly1fxgu8jys3fj21hc0u0k0j.jpg,https://tvax1.sinaimg.cn/large/bfe05ea9ly1fxgunx09dtj21hc0u0q81.jpg,https://tvax1.sinaimg.cn/large/bfe05ea9ly1fxgv2t92yyj21hc0u0qb9.jpg");
            setting.set("avatar", "https://portrait.gitee.com/uploads/avatars/user/772/2317865_qianyucc_1589023575.png");
            setting.set("nickname", "竹林笔墨");
            setting.store(file.getAbsolutePath());
        }*/
        // 若不设置参数默认读取classpath下的文件
        // Linux下读取改地址在外部
        Setting setting = new Setting(settingFilePath, CharsetUtil.CHARSET_UTF_8, false);
        // 自动检测文件更新
        setting.autoLoad(true);
        log.info(BlogSetting.fromSetting(setting).toString());
        return setting;
    }
}
