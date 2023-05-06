package com.hqep.dataSharingPlatform.common.multipleData;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface sssjlDataSource {

    String DATA_SOURCE_1 = "dataSource1";
    String DATA_SOURCE_2 = "dataSource2";
    String DATA_SOURCE_3 = "dataSource3";

    String name() default "dataSource1";
}
