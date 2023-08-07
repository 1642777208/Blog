package com.zyq.service;

import com.zyq.domain.vo.BaseResult;
import org.springframework.web.multipart.MultipartFile;

public interface ClientInfoService {
    public BaseResult uploadAvatar(MultipartFile img);
}
