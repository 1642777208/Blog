package com.zyq.blog.demo1.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.zyq.blog.demo1.exception.BlogException;
import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.enums.ErrorInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

// 全局异常处理类
@Slf4j
@ControllerAdvice // 给所有Controller添加统一操作或处理
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class) // 当抛出NoHandlerFoundException时调用函数
    @ResponseStatus(HttpStatus.NOT_FOUND)                    // 改变状态码
    public BaseResults noHandlerFoundExceptionHandler(NoHandlerFoundException exception) {
        log.error("NoHandlerFoundException:{}", exception.getMessage());
        return BaseResults.fromErrorInfo(ErrorInfoEnum.RESOURCES_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public BaseResults exceptionHandler(Exception exception) {
        exception.printStackTrace();
        log.error("Exception:{}", exception.getMessage());
        return BaseResults.fromErrorInfo(ErrorInfoEnum.UNKNOWN_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = BlogException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResults blogExceptionHandler(BlogException exception) {
        log.error("BlogException:{}", exception.getMsg());
        return exception.toResultVO();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResults handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return BaseResults.error("参数错误", errors);
    }

    @ResponseBody
    @ExceptionHandler(value = JWTDecodeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public BaseResults signatureExceptionHandler(JWTDecodeException exception) {
        log.error("SignatureException:{}", exception.getMessage());
        return BaseResults.fromErrorInfo(ErrorInfoEnum.TOKEN_INVALID);
    }
}
