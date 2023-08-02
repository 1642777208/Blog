package com.zyq.handler;

import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public BaseResult systemExceptionHandler(BlogException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return BaseResult.fail(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public BaseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return BaseResult.fail(BlogErrorInfoEnum.UNKNOWN_ERROR.getCode(),e.getMessage());
    }
}