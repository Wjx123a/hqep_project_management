package com.hqep.dataSharingPlatform.common.utils;

import javax.servlet.ServletException;

/**
 * @类名: MyException1
 * @功能描述 自定义异常类
 * @作者信息 Wang_XD
 * @创建时间 2019/9/1
 */
public class MySessionFilterException extends ServletException {
    public MySessionFilterException(){};

    public MySessionFilterException(PageData message){
        super(String.valueOf(message));
    }
}
