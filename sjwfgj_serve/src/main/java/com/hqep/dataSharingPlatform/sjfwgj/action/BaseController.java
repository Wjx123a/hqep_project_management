package com.hqep.dataSharingPlatform.sjfwgj.action;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: BaseController
 * @author: wjx
 * @data: 2023/5/1 18:23 PM
 */

import com.hqep.dataSharingPlatform.sjfwgj.error.BusinessException;
import com.hqep.dataSharingPlatform.sjfwgj.error.EmBusinessError;
import com.hqep.dataSharingPlatform.sjfwgj.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @ProjectName: Item
 * @ClassName: BaseController
 * @author: wjx
 * @data: 2023/3/13 18:33 PM
 */
//公共的处理未处理异常的方法
public class BaseController {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        HashMap<String, Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }
        else{
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOW_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
