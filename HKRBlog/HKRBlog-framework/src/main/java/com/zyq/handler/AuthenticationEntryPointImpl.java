package com.zyq.handler;

import com.alibaba.fastjson.JSON;
import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hikari
 * security 认证异常处理类
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        BaseResult result = null;
        if (e instanceof BadCredentialsException){
            result = BaseResult.fail(BlogErrorInfoEnum.CHECK_USER_ERR);
        }else if (e instanceof InsufficientAuthenticationException){
            result = BaseResult.fail(BlogErrorInfoEnum.NOT_LOGIN);
        }else {
            result = BaseResult.fail(BlogErrorInfoEnum.UNKNOWN_ERROR);
        }
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
