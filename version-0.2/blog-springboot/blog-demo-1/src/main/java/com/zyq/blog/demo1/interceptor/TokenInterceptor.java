package com.zyq.blog.demo1.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.zyq.blog.demo1.annotation.Token;
import com.zyq.blog.demo1.exception.BlogException;
import com.zyq.blog.demo1.model.enums.AdminEnum;
import com.zyq.blog.demo1.model.enums.ErrorInfoEnum;
import com.zyq.blog.demo1.utils.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(Token.class)) {
            Token jwtToken = method.getAnnotation(Token.class);
            if (jwtToken.required()) {
                // 执行认证
                // 从 http 请求头中取出 token
                String token = request.getHeader("token");
                if (Objects.isNull(token) || token.length() == 0){
                    throw new BlogException(ErrorInfoEnum.NOT_LOGIN);
                }
                // 判断当前请求所需权限
                // 需要管理员权限
                if (jwtToken.admin()){
                    Map<String, Claim> verifyToken = JWTUtils.verifyToken(token);
                    if (AdminEnum.isAdmin(verifyToken.get("username").asString())){
                        return true;
                    }else {
                        throw new BlogException(ErrorInfoEnum.NO_AUTHORITY);
                    }
                }else {
                    return true;
                }
            }
        }else {
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
