package com.hqep.dataSharingPlatform.sjfwgj.error;

/**
 * @ProjectName: Item
 * @ClassName: CommonError
 * @author: wjx
 * @data: 2023/3/9 22:56 PM
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public  CommonError setErrMsg(String errMsg);
}
