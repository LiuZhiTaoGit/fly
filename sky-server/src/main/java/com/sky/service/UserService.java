package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author: liuzt
 * @date: 2024/6/22 - 06 - 22 - 13:38
 * @description: com.sky.service
 * @version: 1.0
 */
public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
