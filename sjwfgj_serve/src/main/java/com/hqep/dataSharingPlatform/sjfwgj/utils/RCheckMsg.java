package com.hqep.dataSharingPlatform.sjfwgj.utils;

import java.util.HashMap;
import java.util.Map;

public class RCheckMsg {
//             | 编码值 | 编码说明               |      |
//            | ------ | ---------------------- | ---- |
//            | 200    | 接口返回成功           |      |
//            | -110   | 参数验证失败           |      |
//            | -101   | token无效或已过期      |      |
//            | -111   | 业务逻辑校验失败       |      |
//            | 500    | 业务逻辑错误，服务异常 |      |

    public  static Map<String,Object> usuMsg(int code, Object data , String success) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",code);
        result.put("data",data);
        result.put("success",success);
        if (code == 200) {
            result.put("mes","接口返回成功");
        } else  if (code == -110) {
            result.put("mes","参数验证失败");
        } else  if (code == -101) {
            result.put("mes","token无效或已过期");
        } else  if (code == 500) {
            result.put("mes","业务逻辑错误，服务异常");
        }
//        System.out.println(result);
        return result;
    }
    public  static Map<String,Object> errorMsg(int code, Object data,String mes) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",code);
        result.put("data",data);
        result.put("mes",mes);
        result.put("success",false);
//        System.out.println(result);
        return result;
    }
    public  static Map<String,Object> successMsg(Object data,String mes) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        result.put("data",data);
        result.put("mes",mes);
        result.put("success",true);
//        System.out.println(result);
        return result;
    }

    public  static Map<String,Object> usuMsg(int code, Object data,String mes,String success) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",code);
        result.put("data",data);
        result.put("mes",mes);
        result.put("success",success);
//        System.out.println(result);
        return result;
    }
}
