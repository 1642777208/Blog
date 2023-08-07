package com.zyq.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ApiModel(description = "用户登录信息")
public class UserInfoVO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "用户电子邮件")
    private String email;
}
