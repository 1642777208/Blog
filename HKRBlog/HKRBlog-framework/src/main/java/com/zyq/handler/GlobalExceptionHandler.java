package com.zyq.handler;

import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public BaseResult systemExceptionHandler(BlogException e){
        //打印异常信息
        log.error("异常: {}",e.getMsg());
        //从异常对象中获取提示信息封装返回
        return BaseResult.fail(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult validationBodyException(MethodArgumentNotValidException exception){

        BindingResult result = exception.getBindingResult();
        String message = result.getAllErrors().get(0).getDefaultMessage();
        /**
         *可解开
         **/
//          if (result.hasErrors()) {
//              List<ObjectError> errors = result.getAllErrors();
//              errors.forEach(p ->{
//                  FieldError fieldError = (FieldError) p;
//                  log.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
//                          "},errorMessage{"+fieldError.getDefaultMessage()+"}");
//              });
//          }
//        返回自定义错误格式
//        fieldError.getDefaultMessage()为实体类中的message
        return BaseResult.fail(BlogErrorInfoEnum.ERROR_PARAMETERS.getCode(),message);
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public BaseResult parameterTypeException(HttpMessageConversionException exception){
        log.error(exception.getCause().getLocalizedMessage());
        return BaseResult.fail(BlogErrorInfoEnum.ERROR_PARAMETERS);
    }

    @ExceptionHandler(Exception.class)
    public BaseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("未知异常: {}",e);
        //从异常对象中获取提示信息封装返回
        return BaseResult.fail(BlogErrorInfoEnum.UNKNOWN_ERROR.getCode(),e.getMessage());
    }
}