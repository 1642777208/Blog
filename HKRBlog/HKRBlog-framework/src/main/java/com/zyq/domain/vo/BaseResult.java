package com.zyq.domain.vo;

import com.zyq.enums.BlogErrorInfoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static com.zyq.enums.StatusCodeEnum.SUCCESS;
import static com.zyq.enums.StatusCodeEnum.FAIL;

@Data
@ApiModel(description = "结果返回类")
public class BaseResult <T> {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息")
    private String msg;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    public static <T> BaseResult<T> success() {
        return buildResult(true, null, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> BaseResult<T> success(T data) {
        return buildResult(true, data, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> BaseResult<T> fail(String message) {
        return buildResult(false, null, FAIL.getCode(), message);
    }

    public static <T> BaseResult<T> fail(Integer code, String message) {
        return buildResult(false, null, code, message);
    }
    public static <T> BaseResult<T> fail(BlogErrorInfoEnum errorInfoEnum) {
        return buildResult(false, null, errorInfoEnum.getCode(), errorInfoEnum.getMsg());
    }

    private static <T> BaseResult<T> buildResult(Boolean flag, T data, Integer code, String message) {
        BaseResult<T> r = new BaseResult<>();
        r.setData(data);
        r.setCode(code);
        r.setMsg(message);
        return r;
    }
}
