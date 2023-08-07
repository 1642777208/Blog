package com.zyq.filter;

import com.alibaba.fastjson.JSON;
import com.zyq.domain.entity.LoginUser;
import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.enums.RedisKeyEnum;
import com.zyq.utils.JWTUtils;
import com.zyq.utils.RedisCache;
import com.zyq.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //说明该接口不需要登录  直接放行
            filterChain.doFilter(request, response);
            return;
        }
        String userid = null;
        //解析获取userid
        try {
            userid = JWTUtils.parseToken(token);
        }catch (Exception e){
            e.printStackTrace();
            // 由于还没有进controller无法被全局异常处理捕捉这里手动发出
            WebUtils.renderString(response, JSON.toJSONString(BaseResult.fail(BlogErrorInfoEnum.TOKEN_INVALID)));
            return;
        }
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(RedisKeyEnum.CLIENT_LOGIN + userid);
        //如果获取不到
        if (Objects.isNull(loginUser)){
            WebUtils.renderString(response, JSON.toJSONString(BaseResult.fail(BlogErrorInfoEnum.NOT_LOGIN)));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }


}
