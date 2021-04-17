package com.cloud.lsw.server.logger;

import com.cloud.lsw.server.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * @author lisw
 * @create 2021/4/16 19:57
 */
@EnableAspectJAutoProxy
@Aspect
@Lazy(false)
@Slf4j
public class GetMetadataInterfaceLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMetadataInterfaceLogger.class);

    @Resource
    private ServerService serverService;

    /**声明一个切面*/
    @Pointcut("execution(* com.cloud.lsw.server.service.*.*(..))")
    public void logPointCut(){}

    /**上传的前置通知*/
    @Before(value = "logPointCut()")
    public void beforeUpload(JoinPoint joinPoint){
        LOGGER.info("用户开始获取元数据");
    }

    /**上传的后置通知*/
    @After(value = "logPointCut()")
    public void afterUpload(JoinPoint joinPoint){
        LOGGER.info("用户获取元数据结束");
    }

    /**上传文件的返回通知*/
    @AfterReturning(value = "logPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        LOGGER.info("用户获取的元数据为：" + result.toString());
    }


    /**异常通知*/
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String name = joinPoint.getSignature().getName();
        LOGGER.info(name + "方法抛异常了，异常是：" + e.getMessage());
    }
}
