package com.hqep.dataSharingPlatform.common.multipleData;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Component
@Aspect
public class sssjlDataSourceAspect {


    private static final Logger logger = LoggerFactory.getLogger(sssjlDataSourceAspect.class);

    /**
     * 拦截目标方法，获取由@sssjlDataSource 指定的数据源标识，设置到线程存储中以便切换数据源
     * @param point
     * @throws Exception
     */
//    @Around(value = "@within(sssjlDataSource) || @annotation(sssjlDataSource)")
    @Before(value = "@within(sssjlDataSource) || @annotation(sssjlDataSource)")
    public void intercept(JoinPoint point) throws Exception
    {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method)
    {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(sssjlDataSource.class)) {
                sssjlDataSource source = clazz.getAnnotation(sssjlDataSource.class);
                sssjlDynamicDataSourceHolder.setDataSource(source.name());
            }

            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(sssjlDataSource.class)) {
                sssjlDataSource source = m.getAnnotation(sssjlDataSource.class);
                sssjlDynamicDataSourceHolder.setDataSource(source.name());
            }
        } catch (Exception e) {
            logger.error("数据源切换出现异常", e);
        }
    }

}
