package com.zyq.blog.demo1.controller;

import cn.hutool.crypto.digest.MD5;
import com.zyq.blog.demo1.annotation.Token;
import com.zyq.blog.demo1.exception.BlogException;
import com.zyq.blog.demo1.model.comm.BaseResults;
import com.zyq.blog.demo1.model.enums.ErrorInfoEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {
    @Token(admin = true)
    @PostMapping("/upload")
    public BaseResults upload(@RequestParam("file") MultipartFile fileUpload) {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名 项目小整个MD5加密去重
        try {
            fileName = MD5.create().digestHex(fileUpload.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileName = fileName + suffixName;
        //获取项目图片保存的地址
        String staticPath = "/home/blog/images/";
        // 检查是否有该文件如果文件存在就直接返回
        File file = new File(staticPath + fileName);
        if (file.exists()) {
            //返回提示信息
            return BaseResults.ok("上传成功", fileName);
        } else {
            //若文件不存在就保存
            try {
                fileUpload.transferTo(file);
            } catch (IOException e) {
                throw new BlogException(ErrorInfoEnum.FILE_UPLOAD_ERROR);
            }
        }

        //返回提示信息
        return BaseResults.ok("上传成功", fileName);
    }
}
