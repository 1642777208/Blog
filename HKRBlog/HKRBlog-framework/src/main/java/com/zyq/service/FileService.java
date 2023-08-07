package com.zyq.service;

import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.PreFilePathEnum;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public BaseResult uploadImg(MultipartFile img, PreFilePathEnum preFilePathEnum);
}
