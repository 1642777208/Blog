package com.zyq.service.impl;

import com.zyq.domain.dto.LoginDTO;
import com.zyq.domain.entity.LoginUser;
import com.zyq.domain.vo.BaseResult;
import com.zyq.domain.vo.ClientLoginVO;
import com.zyq.domain.vo.UserInfoVO;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.enums.RedisKeyEnum;
import com.zyq.exception.BlogException;
import com.zyq.service.LoginService;
import com.zyq.utils.BeanCopyUtils;
import com.zyq.utils.JWTUtils;
import com.zyq.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public BaseResult login(LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        // 会自动调用UserDetailsService进行验证若验证失败则authenticate为null
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 判断是否验证成功
        if (Objects.isNull(authenticate)) {
            throw new BlogException(BlogErrorInfoEnum.CHECK_USER_ERR);
        }
        // 获取userid存入token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JWTUtils.createToken(userId);
        // 验证成功则将用户信息存入redis
        redisCache.setCacheObject(RedisKeyEnum.CLIENT_LOGIN + userId, loginUser);
        // 把token和userInfo封装返回
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        return BaseResult.success(new ClientLoginVO(token, userInfoVO));
    }

    @Override
    public BaseResult logout() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisCache.deleteObject(RedisKeyEnum.CLIENT_LOGIN + loginUser.getUser().getId().toString());
        return BaseResult.success();
    }
}
