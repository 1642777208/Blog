package com.zyq.service.impl;

import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.PreFilePathEnum;
import com.zyq.service.ClientInfoService;
import com.zyq.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClientInfoServiceImpl implements ClientInfoService {

    @Autowired
    private FileService fileService;

    @Override
    public BaseResult uploadAvatar(MultipartFile img) {
        return fileService.uploadImg(img, PreFilePathEnum.OSS_CLIENT_IMG);
    }
}
