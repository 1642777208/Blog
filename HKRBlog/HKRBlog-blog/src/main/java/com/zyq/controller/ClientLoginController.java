package com.zyq.controller;

import com.zyq.domain.dto.LoginDTO;
import com.zyq.domain.vo.BaseResult;
import com.zyq.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@Api(tags = "登录模块")
public class ClientLoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResult clientLogin(@RequestBody @Validated LoginDTO loginDTO){
        return loginService.login(loginDTO);
    }
    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public BaseResult clientLogout(){
        return loginService.logout();
    }
}
