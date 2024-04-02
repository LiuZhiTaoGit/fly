package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: liuzt
 * @date: 2024/4/2 - 04 - 02 - 10:13
 * @description: com.annotation  自定义注解类， 自动填充
 * @version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFile {
    /**
     * 数据库的操作类型
     * @return
     */
    OperationType value();
}
