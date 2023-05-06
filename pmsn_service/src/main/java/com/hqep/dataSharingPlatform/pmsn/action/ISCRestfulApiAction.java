package com.hqep.dataSharingPlatform.pmsn.action;


import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.ISCUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.sgcc.isc.core.orm.organization.BusinessOrganization;
import com.sgcc.isc.core.orm.role.OrganizationalRole;
import com.sgcc.isc.ualogin.client.vo.IscSSOUserBean;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @类名: NlkfRestfulApiAction
 * @功能描述 能力开放api接口
 * @作者信息 sssJL
 * @创建时间 2021/12/06
 */
@RequestMapping("/iscApi")
@RestController
@Api(description = "ISC的api接口")
public class ISCRestfulApiAction {


    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @方法名:org
     * @功能描述:获取用户信息
     * @作者信息：sssJL
     * @创建时间:9:13 2022/3/23
     **/
    @RequestMapping(value="/userinfo", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    ResultBodyBean userinfo(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                String username = pd.getString("username");
                String password = pd.getString("password");
                String userinfo = ISCUtil.prod_get_userinfo(username,password,request);
                resultBodyBean.setData(userinfo);
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }

    /**
     * @方法名:org
     * @功能描述:获取用户信息
     * @作者信息：sssJL
     * @创建时间:9:13 2022/3/23
     **/
    @RequestMapping(value="/sessioninfo", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    ResultBodyBean sessioninfo(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                String username = pd.getString("username");
                String password = pd.getString("password");
                IscSSOUserBean iscSSOUserBean = ISCUtil.getISCUserInfoBean(request);
                resultBodyBean.setData(iscSSOUserBean);
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }


    /**
     * @方法名:org
     * @功能描述:获取用户信息
     * @作者信息：sssJL
     * @创建时间:9:13 2022/3/23
     **/
    @RequestMapping(value="/oroles", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    ResultBodyBean oroles(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                String userId = pd.getString("userId");
                List<OrganizationalRole> ors = ISCUtil.getOrgRolesByUserId1(userId);
                resultBodyBean.setData(ors);
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }



    /**
     * @方法名:org
     * @功能描述:获取组织机构
     * @作者信息：sssJL
     * @创建时间:9:13 2022/3/23
     **/
    @ApiOperation(value = "获取组织机构", notes = "获取组织机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/org", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    ResultBodyBean org(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                List<OrganizationalRole> orglist = ISCUtil.getOrgRolesByUserId(request);
                resultBodyBean.setData(orglist);
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }



    /**
     * @方法名:org1
     * @功能描述:获取组织机构
     * @作者信息：sssJL
     * @创建时间:9:13 2022/3/23
     **/
    @ApiOperation(value = "获取组织机构", notes = "获取组织机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/org1", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    ResultBodyBean org1(String userId) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!userId.isEmpty() && userId != "" && userId != null) {
                List<OrganizationalRole> orglist = ISCUtil.getOrgRolesByUserId1(userId);
                resultBodyBean.setData(orglist);
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }

}
