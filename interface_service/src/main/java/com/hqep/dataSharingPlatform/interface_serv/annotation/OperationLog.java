package com.hqep.dataSharingPlatform.interface_serv.annotation;

import java.lang.annotation.*;

/**
 * create by sssjl
 * create time 2021/12/09 13:30
 * description 自定义注解，为了“数据管理运营月报”功能使用
 */
@Target(ElementType.METHOD) //目标是方法
@Retention(RetentionPolicy.RUNTIME) //注解会在class中存在，运行时可通过反射获取
@Documented //文档生成时，该注解将被包含在javadoc中，可去掉
public @interface OperationLog {
    String value() default "";
}
