package com.zyq.blog.demo1.model.vo;

public interface ConverterInter<T,E> {
    /**
     * VO 转换函数
     *
     * @param t 目标对象
     * @return 转换结果
     */
    E convertToVO(T t);
}
