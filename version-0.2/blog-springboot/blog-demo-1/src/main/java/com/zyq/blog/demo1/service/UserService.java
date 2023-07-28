package com.zyq.blog.demo1.service;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.setting.Setting;
import com.zyq.blog.demo1.exception.BlogException;
import com.zyq.blog.demo1.model.dto.UserDTO;
import com.zyq.blog.demo1.model.enums.ErrorInfoEnum;
import com.zyq.blog.demo1.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private Setting setting;

    public String checkUser(UserDTO userDTO) {
        String username = setting.getStr("username");
        String password = setting.getStr("password");
        if (Objects.equals(username, userDTO.getUsername()) &&
                Objects.equals(password, MD5.create().digestHex(userDTO.getPassword()))) {
            return JWTUtils.createToken(userDTO);
        }else {
            throw new BlogException(ErrorInfoEnum.CHECK_USER_ERR);
        }
    }
}
