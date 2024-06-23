package com.sky.controller.user;

import com.sky.config.RedisConfiguration;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author: liuzt
 * @date: 2024/6/22 - 06 - 22 - 10:43
 * @description: com.sky.controller.admin
 * @version: 1.0
 */
@RestController("userShopController")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "店铺状态相关接口")
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/status")
    @ApiOperation(value = "获取店铺的营业状态")
    public Result<Integer> getStatus(){
        Integer shop_status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("当前店铺的营业状态为：{}", shop_status);
        return Result.success(shop_status);

    }

}
