package com.zyq.blog.demo1.model.enums;

public interface IErrorInfo {
    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMsg();
    /**
     * 获取错误码
     * @return 错误码
     */
    int getCode();
}
