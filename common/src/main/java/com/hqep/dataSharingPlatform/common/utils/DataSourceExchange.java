package com.hqep.dataSharingPlatform.common.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class DataSourceExchange {

    private static Logger logger = LoggerFactory.getLogger(DataSourceExchange.class);

    public Object execute(ProceedingJoinPoint pjp) {

        logger.info("DataSourceExchange==>");
        Object obj = null;

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Method realMethod = null;
        try {
            realMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
            if (realMethod.isAnnotationPresent(DataSource.class)) {
                DataSource datasource = (DataSource) realMethod.getAnnotation(DataSource.class);
                DataSourceContext.setDbType(datasource.name());
                logger.info(realMethod.getName() + " set dbtype==>" + datasource.name());
            } else {
                DataSourceContext.setDbType("dataSourceWrite");
            }
            obj = pjp.proceed();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        logger.info(realMethod.getName() + " clear datatype==>");
        DataSourceContext.clearDbType();
        return obj;

    }

}
