package com.hqep.dataSharingPlatform.common.exception;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/5$ 14:46$
 * @Version: 1.0
 */
public class CustomException extends RuntimeException{

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public CustomException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    public CustomException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }
}
