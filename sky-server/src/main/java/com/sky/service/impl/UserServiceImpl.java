package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liuzt
 * @date: 2024/6/22 - 06 - 22 - 14:27
 * @description: com.sky.service.impl
 * @version: 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;


    @Autowired
    private UserMapper userMapper;
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        log.info("微信用户登录：{}",userLoginDTO.getCode());

//        调用微信接口服务，获得当前微信用户的openid

        String openid = getOpenId(userLoginDTO.getCode());


//        判断openid是否为空，如果为空则表示登录失败， 抛出业务异常
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

//        判断当前用户是否为新用户
        User byOpenId = userMapper.getByOpenId(openid);

//        如果是新用户，自动完成注册
        if(byOpenId == null){
            byOpenId = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(byOpenId);

        }
//         返回这个用户的对象
        return byOpenId;

    }


    //        调用微信接口服务，获得当前微信用户的openid
    private String getOpenId(String code){
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }


}
