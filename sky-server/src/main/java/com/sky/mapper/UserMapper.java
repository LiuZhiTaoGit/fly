package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author: liuzt
 * @date: 2024/6/22 - 06 - 22 - 14:41
 * @description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where openid = #{openid}")
    User getByOpenId(String openid);


    void insert(User byOpenId);

}
