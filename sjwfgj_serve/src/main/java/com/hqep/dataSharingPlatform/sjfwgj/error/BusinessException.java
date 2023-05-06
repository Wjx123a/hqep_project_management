package com.hqep.dataSharingPlatform.sjfwgj.error;

/**
 * @ProjectName: Item
 * @ClassName: BusinessException
 * @author: wjx
 * @data: 2023/3/10 23:48 PM
 */
public class BusinessException extends Exception implements CommonError{
    private CommonError commonError;
    //    接收自定义errMsg方式构造业务异常
    public BusinessException (CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    public BusinessException(CommonError commonError){
        //    接收EmBUsinessError的传参用于构造业务异常
        super();
        this.commonError = commonError;
    }
    @Override
    public int getErrCode() {
        return commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
