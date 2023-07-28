package com.zyq.blog.demo1.exception;

import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.enums.IErrorInfo;

public class BlogException extends RuntimeException{
    private final IErrorInfo errorInfo;

    public BlogException(IErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getMsg(){
        return errorInfo.getMsg();
    }

    /**
     * 将异常转换为 ResultVO 对象返回给前端
     *
     * @return 封装了异常信息的 ResultVO 对象
     */
    public BaseResults toResultVO() {
        return BaseResults.fromErrorInfo(errorInfo);
    }
}
