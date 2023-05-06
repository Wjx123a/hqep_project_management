package com.hqep.dataSharingPlatform.pmsn.action;

import com.alibaba.fastjson.JSON;
import com.hqep.dataSharingPlatform.common.utils.*;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.pmsn.service.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @类名: LoginAction
 * @功能描述 登陆模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/20
 */
@RequestMapping("/loginAction")
@RestController
@Api(description = "登陆模块")
public class LoginAction {

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @Autowired
    protected LoginService loginService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @方法名:login
     * @功能描述:登陆系统
     * @作者信息：Wang_XD
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "登陆系统", notes = "登陆系统")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PostMapping("/login")
    @RequestLog("登陆系统")
    public @ResponseBody
    ResultBodyBean login(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            System.out.println(pd);
            if (!pd.isEmpty()) {
                resultBodyBean = loginService.loginService(pd);
                status = HttpStatus.OK.value();
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.setStatus(status);
            System.out.println("login==>out==>resultBodyBean:"+ resultBodyBean);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }


    /**
     * @方法名:updatePasswrod
     * @功能描述:修改密码
     * @作者信息：Wang_XD
     * @创建时间:15:56 2019/9/23
     **/
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = false, value = "颁发token验证信息",
                    defaultValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZGIyMjQ0Yi1jMzY2LTQxM2ItOTAxZS01OTA1OTVkMDYxODciLCJpc3MiOiJkb25nZG9uZ0NlbnRlciIsInN1YiI6Ijk2NDlkMzgyOGFhNDRmY2U5OWQwNTAwYjI5ZjdhNzcxIiwiaWF0IjoxNTcxMDM2OTY0LCJuYW1lIjoiYWFhIiwicm9sZUlkIjoiMTMwMiIsInJvbGVOYW1lIjoi5pmu6YCa55So5oi3IiwicGhvbmUiOiIxNzc0NTY4NDU2OCIsImlzQWRtaW4iOiIwIiwiZXhwIjoxNTcxOTAwOTY0fQ.cAhu1uJfFsLKYtHa9WNNiQNZRWYe8l7iJmqnjMQv5i4"),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PutMapping("/updatePasswrod")
    public ResultBodyBean updatePasswrod(@RequestBody PageData pd, @RequestHeader String Authorization) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                if (StringUtil2.isEmpty(pd.getString("loginNum"))) {
                    errorReasonBean.setCode("428");
                    errorReasonBean.setText("登陆名不能为空!");
                    status = HttpStatus.PRECONDITION_REQUIRED.value();
                } else {
                    Jws<Claims> parseJwt = JwtUtil.parseJwt(Authorization);
                    String name = (String) parseJwt.getBody().get("name");
                    String id = (String) parseJwt.getBody().get("personId");
                    pd.put("optId", id);
                    pd.put("optName", name);
                    pd.put("optExplain", "更改密码");
                    resultBodyBean = loginService.updatePasswordService(pd);
                    status = HttpStatus.OK.value();
//                    ISCUtil iscUtil = new ISCUtil();
//                    resultBodyBean.setData(iscUtil.getUsersByLoginCode(request));
                }
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setText(ResponseJsonApiUtil.getErrorMsg("Exception", e.getClass().getSimpleName()).toString());
            resultBodyBean.setError(errorReasonBean);
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }

    /**
     * @方法名:resetPassword
     * @功能描述:重置密码
     * @作者信息：
     * @创建时间:
     **/
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public PageData resetPassword(@RequestBody PageData pd) {
        System.out.println("11111111");
        PageData pageData = new PageData();
        try {
            loginService.resetPassword(pd);
            pageData.put("data", "success");
            pageData.put("error", null);
        } catch (Exception e) {
            pageData.put("error", "密码重置错误！");
            e.printStackTrace();
        } finally {
            pageData.put("error", "密码重置错误！");

        }
        return pageData;
    }


}
