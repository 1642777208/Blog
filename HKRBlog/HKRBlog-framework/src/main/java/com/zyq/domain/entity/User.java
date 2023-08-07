package com.zyq.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-08-03 20:58:31
 */
@SuppressWarnings("serial")
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("user")
public class User {
    //用户id
    @TableId
    private Integer id;
    //用户昵称
    private String nickname;
    //用户名
    private String username;
    //密码
    private String password;
    //头像
    private String avatar;
    //个人简介
    private String intro;
    //电子邮件
    private String email;
    //登录ip
    private String ipAddress;
    //登录地址
    private String ipSource;
    //登录方式 (1邮箱 2QQ 3Gitee 4Github)
    private Integer loginType;
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //登录时间
    private Date loginTime;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //是否为管理员(0否 1是)
    private Integer type;

}

