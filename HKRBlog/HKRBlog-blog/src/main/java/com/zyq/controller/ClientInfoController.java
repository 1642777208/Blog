package com.zyq.controller;

import com.zyq.domain.vo.BaseResult;
import com.zyq.service.ClientInfoService;
import com.zyq.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api("用户信息接口")
public class ClientInfoController {

    @Autowired
    private ClientInfoService clientInfoService;

    @ApiOperation("上传图片")
    @PostMapping("/upload/img")
    public BaseResult uploadImg(MultipartFile img){
        return clientInfoService.uploadAvatar(img);
    }

}
