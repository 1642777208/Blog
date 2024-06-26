package com.zyq.blog.demo1.model.dto;

public interface ConverterInter<T,E> {
    /**
     * 将对应的 DTO 转换为 PO
     * @param t 需要转换的 DTO 类
     * @return 转换结果
     */
    E convertToPO(T t);
}