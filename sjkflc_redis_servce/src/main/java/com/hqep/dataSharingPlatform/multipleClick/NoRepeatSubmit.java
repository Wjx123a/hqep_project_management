package com.hqep.dataSharingPlatform.multipleClick;

import java.lang.annotation.*;

/**
 * @author Tzeao
 */
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Documented
public @interface NoRepeatSubmit {

    //名称，如果不给就是要默认的
    String name() default "name";
}
