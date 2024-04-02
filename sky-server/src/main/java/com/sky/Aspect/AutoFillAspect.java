package com.sky.Aspect;

import com.sky.annotation.AutoFile;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: liuzt
 * @date: 2024/4/2 - 04 - 02 - 10:34
 * @description: com.Aspect  自定义切面类， 统一为公共字段赋值
 * @version: 1.0
 */
@Aspect   // 切面声明，标注在类、接口（包括注解类型）或者枚举上
@Component   //将该类交给bean容器
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     *  //切入点声明，即切入到那些目标欸的目标方法。 既可以使用execution，也可以使用自定义注解
     *  返回值是所有的 *
     *  包：com.sky.mapper
     *  所有的类 *
     *  所有的方法匹配所有的参数类型  *（..）
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFile)")
    public void autoFillPointCut(){};

    /**
     * 通知，自动填充公共字段
     * @param joinPoint
     */
    @Before("autoFillPointCut()")  //要指定切入点
    public void autoinfo(JoinPoint joinPoint){   //传入连接点
        log.info("开始进行公共字段自动填充");

        //需要完成三件事
        //第一： 获得方法的签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //第二  获得方法的注解
        AutoFile autoFile = signature.getMethod().getAnnotation(AutoFile.class);
        //第三获得注解中的操作类型
        OperationType operationType = autoFile.value();
        //获得当前目标方法的参数
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return ;
        }
//        然后就是赋值
        //实体对象
        Object entity = args[0];
        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();


        if(operationType == OperationType.INSERT){
            //当前执行的是insert操作，为4个字段赋值
            try {
                //反射  获得set方法对象，
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //通过反射调用目标对象的方法
                setCreateTime.invoke(entity,now);
                setUpdateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateUser.invoke(entity,currentId);

            } catch (Exception e) {

            }
        }else{//执行的是update操作，为两个字段赋值
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            }catch (Exception e){
                log.info("自动填充失败：{}",e.getMessage());
            }

        }


    }

}
