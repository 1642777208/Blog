package com.zyq.blog.demo1.model.comm;

import com.zyq.blog.demo1.model.enums.IErrorInfo;
import com.zyq.blog.demo1.utils.BlogUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;


/*
* @Data lombok 自动配置getter和setter
* @Builder lombok 建造者模式
* @Accessors(chain = true) lombok chain设置setter方法返回该对象
* */
@Data
@Accessors(chain = true)
@Builder
@ApiModel("通用接口返回对象")
public class BaseResults<T> {
    @ApiModelProperty(required = true, notes = "结果码", example = "0")
    private int code;
    @ApiModelProperty(required = true, notes = "返回信息", example = "操作成功")
    private String msg;
    @ApiModelProperty(required = true, notes = "返回数据", example = "{\"id\":2001}")
    private T data;
    @ApiModelProperty(required = true, notes = "时间戳", example = "2022-10-10 09:07:34")
    private String timestamp;
    public static <T> BaseResults<T> ok(T data) {
        return BaseResults.<T>builder()
                .msg("操作成功")
                .data(data)
                .timestamp(BlogUtils.now())
                .build();
    }

    public static BaseResults fromErrorInfo(IErrorInfo errorInfo) {
        return BaseResults.builder()
                .code(errorInfo.getCode())
                .msg(errorInfo.getMsg())
                .timestamp(BlogUtils.now())
                .build();
    }

    public static <T> BaseResults<T> ok(String msg, T data) {
        return BaseResults.<T>builder()
                .msg(msg)
                .data(data)
                .timestamp(BlogUtils.now())
                .build();
    }

    public static <T> BaseResults<T> error(T data) {
        return BaseResults.<T>builder()
                .code(5000)
                .msg("操作失败")
                .data(data)
                .timestamp(BlogUtils.now())
                .build();
    }

    public static <T> BaseResults<T> error(String msg, T data) {
        return BaseResults.<T>builder()
                .code(5000)
                .msg(msg)
                .data(data)
                .timestamp(BlogUtils.now())
                .build();
    }
}
