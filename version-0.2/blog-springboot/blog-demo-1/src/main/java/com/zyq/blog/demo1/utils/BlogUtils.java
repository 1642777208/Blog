package com.zyq.blog.demo1.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BlogUtils {
    /**
     * 以 Java8 的方式获取当前时间字符串
     *
     * @return 当前时间格式化之后的字符串
     */
    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 以 Java8 的方式将毫秒数转化为字符串，字符串格式：yyyy-MM-dd HH:mm
     *
     * @param millis 毫秒数
     * @return 格式化之后的字符串
     */
    public static String formatDatetime(Long millis) {
        if (millis == null) {
            return "";
        }
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
