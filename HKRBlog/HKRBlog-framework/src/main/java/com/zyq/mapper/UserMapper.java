package com.zyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyq.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * (User)表数据库访问层
 *
 * @author hikari
 * @since 2023-08-03 20:58:32
 */
public interface UserMapper extends BaseMapper<User> {

}
