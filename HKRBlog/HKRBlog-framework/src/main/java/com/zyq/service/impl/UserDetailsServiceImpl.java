package com.zyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zyq.domain.entity.LoginUser;
import com.zyq.domain.entity.User;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.exception.BlogException;
import com.zyq.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(queryWrapper);

        if (Objects.isNull(user)){
            throw new BlogException(BlogErrorInfoEnum.USER_NOT_FOUND);
        }

        return new LoginUser(user);
    }
}
