package com.zyq.blog.demo1.controller;

import cn.hutool.core.map.MapUtil;
import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.dto.UserDTO;
import com.zyq.blog.demo1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Api("与用户相关的api接口")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public BaseResults<Map<String, Object>> login(@ApiParam(name = "用户登录信息", value = "传入json格式", required = true)
                                              @RequestBody @Valid UserDTO userDTO) {
        String token = userService.checkUser(userDTO);
        return BaseResults.ok("登录成功", MapUtil.of("token", token));
    }

    /**
     * 暂时没实现服务器强制失效，因为是一个人用让前端删token
     * @param
     * @return
     */

    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public BaseResults<String> logout(/*@ApiParam(name = "用户登录信息", value = "传入json格式", required = true)
                                                  @RequestBody @Valid UserDTO userDTO*/) {
//        String token = userService.checkUser(userDTO);
        return BaseResults.ok("退出登录成功","");
    }
}