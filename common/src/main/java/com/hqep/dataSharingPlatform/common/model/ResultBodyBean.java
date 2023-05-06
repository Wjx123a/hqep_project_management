package com.hqep.dataSharingPlatform.common.model;


/**
 * @类名: ResultBodyBean
 * @功能描述 登陆返回实体
 * @作者信息 Wang_XD
 * @创建时间 2019/9/23
 */
public class ResultBodyBean {

    private Object data;
    private ErrorReasonBean error;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ErrorReasonBean getError() {
        return error;
    }

    public void setError(ErrorReasonBean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ResultBodyBean{" +
                "data=" + data +
                ", error=" + error +
                '}';
    }

    public ResultBodyBean(Object data, ErrorReasonBean error) {
        super();
        this.data = data;
        this.error = error;
    }

    public ResultBodyBean(){
        super();
    }
}
