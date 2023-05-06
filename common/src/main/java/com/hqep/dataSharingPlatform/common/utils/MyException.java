package com.hqep.dataSharingPlatform.common.utils;

/**
 * @类名: MyException
 * @功能描述 自定义异常类
 * @作者信息 Wang_XD
 * @创建时间 2019/9/1
 */
public class MyException extends RuntimeException{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2611061967444672628L;

    public MyException(String message) {
        super(message);
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
