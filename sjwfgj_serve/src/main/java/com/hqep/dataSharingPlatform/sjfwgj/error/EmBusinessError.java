package com.hqep.dataSharingPlatform.sjfwgj.error;

/**
 * @ProjectName: Item
 * @ClassName: EmBusinessError
 * @author: wjx
 * @data: 2023/3/10 23:36 PM
 */
public enum EmBusinessError implements CommonError{
//    通用错误类型10000
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOW_ERROR(10002,"发生未知错误"),
//    20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_ERROR(20002,"用户密码或手机号信息有误！"),
    USER_NOT_LOGIN(20003,"用户还未登录"),
//    30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(30001,"库存不足"),


    ;

    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
