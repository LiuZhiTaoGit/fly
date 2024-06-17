package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: liuzt
 * @date: 2024/4/2 - 04 - 02 - 10:13
 * @description: com.annotation  自定义注解类， 自动填充  修改   annotation（注解）
 * @version: 1.0
 */
@Target(ElementType.METHOD)  //注解只能在方法上
@Retention(RetentionPolicy.RUNTIME)//
public @interface AutoFile {
    /**
     * 数据库的操作类型
     * @return
     */
    OperationType value();
}
