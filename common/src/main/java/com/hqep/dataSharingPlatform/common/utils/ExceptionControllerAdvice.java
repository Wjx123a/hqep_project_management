package com.hqep.dataSharingPlatform.common.utils;

import com.hqep.dataSharingPlatform.common.exception.CustomException;
import com.hqep.dataSharingPlatform.common.vo.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/5$ 15:04$
 * @Version: 1.0
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    /*
       捕捉自定义的CustomException异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public JsonResult CommonExceptionHandler(CustomException e) {
        return JsonResult.fail("捕捉到自定义CustomException异常",e);
    }
}