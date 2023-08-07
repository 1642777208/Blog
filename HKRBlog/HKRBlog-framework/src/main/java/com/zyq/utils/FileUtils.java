package com.zyq.utils;

import com.zyq.enums.PreFilePathEnum;
import com.zyq.enums.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.resource.HttpResource;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class FileUtils {

    /**
     * 获取文件md5值
     *
     * @param inputStream 文件输入流
     * @return {@link String} 文件md5值
     */
    public static String getMd5(InputStream inputStream) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5DigestAsHex(inputStream);
        } catch (Exception e) {
            log.error("get md5 error, {}", e.getMessage());
        }
        return md5;
    }

    /**
     * 使用okhttp判断oss文件是否存在，存在返回true，不存在返回false
     * @author hikari
     * @param filePath url地址
     * @return
     **/
    public static boolean ossFileExists(String filePath) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(filePath)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == HttpStatus.OK.value()){
            return false;
        }
        return true;
    }

}
