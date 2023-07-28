package com.zyq.blog.demo1.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;
/*
* 以年为单位的时间线
* */
@Data
@Builder
public class TimelineVO {
    private String year;
    private List<Item> items;

    @Data
    @Builder
    public static class Item {
        private String id;
        private String gmtCreate;
        private String title;
        private String thumbnail;
    }
}
