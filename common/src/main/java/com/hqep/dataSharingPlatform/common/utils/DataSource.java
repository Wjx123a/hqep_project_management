package com.hqep.dataSharingPlatform.common.utils;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    String DATA_SOURCE_1 = "mysqlDataSource1";
    String DATA_SOURCE_2 = "mysqlDataSource2";

    String name() default "mysqlDataSource1";
}
