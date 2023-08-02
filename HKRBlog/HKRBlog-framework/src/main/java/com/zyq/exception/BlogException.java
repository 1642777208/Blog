package com.zyq.exception;

import com.zyq.enums.BlogErrorInfoEnum;
import lombok.Getter;

@Getter
public class BlogException extends RuntimeException{
    private Integer code;
    private String msg;

    public BlogException(BlogErrorInfoEnum errorInfoEnum) {
        super(errorInfoEnum.getMsg());
        this.code = errorInfoEnum.getCode();
        this.msg = errorInfoEnum.getMsg();
    }
}
