package com.zyq.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zyq.domain.vo.BaseResult;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.enums.PreFilePathEnum;
import com.zyq.exception.BlogException;
import com.zyq.service.FileService;
import com.zyq.utils.FileUtils;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@ConfigurationProperties("oss")
@Setter
public class FileServiceImpl implements FileService {
    @Override
    public BaseResult uploadImg(MultipartFile img, PreFilePathEnum preFilePathEnum) {
        String filename = img.getOriginalFilename();
         // 判断图片格式是否正确
        if (!filename.endsWith(".jpg") && !filename.endsWith(".png")){
            throw new BlogException(BlogErrorInfoEnum.FILE_UPLOAD_ERROR);
        }
        String endFileName = filename.substring(filename.lastIndexOf("."));
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        InputStream inputStream = null;
        try {
            inputStream = img.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 流被消耗
        String key = preFilePathEnum.getPath() + FileUtils.getMd5(inputStream) + endFileName;
        String path = url + key;
        // 判断文件是否存在，若存在则直接返回地址
        if (FileUtils.ossFileExists(url + key)){
            return BaseResult.success(path);
        }
        // 上传文件
        ossUploadFile(img, key);

        return BaseResult.success(path);
    }

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String url;

    private void ossUploadFile(MultipartFile img, String key) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        // 加了缺少参数不知道为啥
        // cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(img.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            throw new BlogException(BlogErrorInfoEnum.FILE_UPLOAD_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
