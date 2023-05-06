package com.hqep.dataSharingPlatform.sjfwgj.response;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: CommonReturnType
 * @author: wjx
 * @data: 2023/5/1 18:20 PM
 */
public class CommonReturnType {
    //    返回处理结果：成功/失败
    private String status;
    private Object data;

//    status=success data内返回前端需要的json数据
//    status=fail data内使用通用的错误码格式

    //    定义通用创建方法
    public  static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
