package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liuzt
 * @date: 2024/4/3 - 04 - 03 - 12:56
 * @description: com.sky.config
 * @version: 1.0
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean  //当项目启动之后，就会调用这个方法，创建该对象，交给spring容器管理
    @ConditionalOnMissingBean  //如果有了，就不用在创建了
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云文件上传工具类对象{}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
