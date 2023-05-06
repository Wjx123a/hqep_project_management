package com.hqep.dataSharingPlatform.pmsn.action;

import com.google.gson.GsonBuilder;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.LoginService;
import com.hqep.dataSharingPlatform.pmsn.service.PersonService;
import com.hqep.dataSharingPlatform.pmsn.unit.RestfulHttpClient;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.isc.service.adapter.helper.IIdentityService;
import com.sgcc.isc.service.adapter.utils.AESUtils;
import com.sgcc.isc.service.adapter.utils.CASClient;
import com.sgcc.isc.service.adapter.utils.CASTicket;
import com.sgcc.isc.service.adapter.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("/ISC")
@RestController
@Api(description = "测试ISC登录问题")
public class testIscAction {

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @Autowired
    private PersonService personService;
    @Autowired
    protected LoginService loginService;



    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/LOG", method = {RequestMethod.POST,RequestMethod.GET})
    public ResultBodyBean LOG() {
        System.out.println("进入LOG:");
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        List<User> ulist=null;
        String server = "http://10.166.8.82:17001/isc_sso"; //统一认证服务地址
        String service = "http://10.166.8.157:13000";//业务应用访问地址
        try {
            String username = "30035976"; // pd.getString("loginNum");
            String password = "sgcc@1234"; // pd.getString("password");
            // 查看登录状态
            try {
                password = AESUtils.encrypt(password, AESUtils.keySeed);
            } catch (Exception e) {
                System.err.println("prod_get_userinfo==>密码加密失败");
            }
            CASTicket t = CASClient.getTicketByAES(server + "/v1/tickets", username, password, service);
            String tgt = t.getTicketGrantingTicket();
            System.out.println("tgt:"+tgt);
            String st = t.getServiceTicket();
            System.out.println("st:" + st);
            String userInfo = t.getUserInfo();
            System.out.println("userInfo:"+userInfo);
            String getErrorMessage = t.getErrorMessage();
            System.out.println("getErrorMessage:"+getErrorMessage);
        } catch (Exception e) {
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }








    @ApiOperation(value = "isc用户登录", notes = "isc用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
    public ResultBodyBean login(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            Map<String,String> map = new HashMap();
            map.put("access_token",pd.getString("access_token"));
//        http://10.166.8.82:17001/isc_sso/oauth2.0/profile?access_token=TGT-355-YvAgCrEWD9hScx35wSdA4sltxLy3XJXgD5kMrFL5m3tV3svH0O-10.166.8.82
            String NR_REST_API = "http://10.166.3.29:7111/isc_sso/oauth2.0/profile";
            String obj = new RestfulHttpClient().getRestApi(map,NR_REST_API);
//            System.out.println("isc用户登录obj:"+obj);
//            System.out.println("输出转JsontoMap:"+obj);
//            System.out.println(new GsonBuilder().serializeNulls().create().fromJson(obj, Map.class));
//            String obj = "{\"provinceId\":\"hl\",\"logintime\":1647242124531,\"name\":\"胡航帆\"
//            ,\"pwdBeforeDecode\":\"\",\"baseOrgId\":\"EC3D682521F40C97E0430100007F78C9\"
//            ,\"iscUserId\":\"EA0850F326E8C5BAE040007F0100203D\",\"iscAdCode\":\"00010002\"
//            ,\"passWord\":\"\",\"scop\":\"0\",\"iscUserSourceId\":\"00010002\",\"ip\":\"10.167.77.88\"}";

//            {roleName=普通用户, loginNum=isc001, password=f64b8e34e4eea8f8c5bef8d87b1d0215,
//            phone=18246001111, name=isc001, orgCode=2400,
//            orgName=国网黑龙江省电力本部, roleId=a99dd3f9-3448-11eb-87e0-0025fd021000,
//            depName=发展部, depCode=, id=0a2ab06bacfd40799f1dba37a5e33a2f}
            Map result = new GsonBuilder().serializeNulls().create().fromJson(obj, Map.class);
            System.out.println("==============================================");
            System.out.println("=========进入isc跳转=======================");
            try {
                String user_id = UUID.randomUUID().toString().replaceAll("-", "");
                PageData userPd = new PageData();
                userPd.put("roleName","普通用户");
                userPd.put("loginNum",(String)result.get("iscAdCode"));
                userPd.put("password","SGCC@1234");
                userPd.put("phone","12311111111");
                userPd.put("name",(String)result.get("name"));
                userPd.put("orgCode","2400");
                userPd.put("orgName","国网黑龙江省电力本部");
                userPd.put("roleId","a99dd3f9-3448-11eb-87e0-0025fd021000");
                userPd.put("depName","营销部");
                userPd.put("depCode","");
                userPd.put("iscflag","1");
                userPd.put("user_id",user_id);
                System.out.println(userPd);
                personService.savePersonInfo(userPd);
                System.out.println("=========进入isc跳转=》创建账号=======================");
                loginService.firstMenuAuth(userPd);
                System.out.println("=========进入isc跳转=》授予初始权限=======================");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!result.isEmpty()) {
                map = new HashMap();
                map.put("iscUserId", (String) result.get("iscUserId"));
                System.out.println("=========进入isc跳转=》调用isc接口=======================");
//                String allInfo = new RestfulHttpClient().getRestApiForAll(map,NR_REST_API);
//                System.out.println("全部allInfo:"+allInfo);
                pd.put("loginNum",(String)result.get("iscAdCode"));
                pd.put("password","0a5ac338913cc1ad1b8e5268c7e89973");
                resultBodyBean = loginService.loginService(pd);
                System.out.println("=========进入isc跳转=》是否存在用户=======================");
                if (resultBodyBean.getError() != null) {
                    PageData paramPd = new PageData();
                    paramPd.put("loginNum","ISCMRCX");
                    paramPd.put("password","0a5ac338913cc1ad1b8e5268c7e89973");
                    paramPd.put("iscUserName",(String) result.get("name"));
                    paramPd.put("iscUserId",(String) result.get("iscUserId"));
                    resultBodyBean = loginService.loginService(paramPd);
                    System.out.println("登录信息："+resultBodyBean);
                    status = HttpStatus.OK.value();
                } else {
                    if ( result.get("username") != null &&
                            "l_shaokx".equals((String) result.get("username"))){
                        PageData paramPd = new PageData();
                        paramPd.put("loginNum","sqqx");
                        paramPd.put("password","0a5ac338913cc1ad1b8e5268c7e89973");
                        paramPd.put("iscUserName",(String) result.get("name"));
                        paramPd.put("iscUserId",(String) result.get("iscUserId"));
                        resultBodyBean = loginService.loginService(paramPd);
                        System.out.println("登录信息："+resultBodyBean);
                        status = HttpStatus.OK.value();
                    } else {
                        PageData paramPd = new PageData();
                        paramPd.put("loginNum",(String)result.get("iscAdCode"));
                        paramPd.put("password","0a5ac338913cc1ad1b8e5268c7e89973");
                        paramPd.put("iscUserName",(String) result.get("name"));
                        paramPd.put("iscUserId",(String) result.get("iscUserId"));
                        resultBodyBean = loginService.loginService(paramPd);
                        System.out.println("登录信息："+resultBodyBean);
                        status = HttpStatus.OK.value();
                    }
                }
            }
        } catch (Exception e) {
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/LOG1", method = {RequestMethod.POST,RequestMethod.GET})
    public ResultBodyBean LOG1() {
        System.out.println("进入LOG1:");
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        List<User> ulist=null;
        String server = "http://10.166.8.82:17001/isc_sso"; //统一认证服务地址
        String service = "http://10.166.8.157:13001";//业务应用访问地址
        try {
            String username = "30035976"; // pd.getString("loginNum");
            String password = "sgcc@1234"; // pd.getString("password");
            // 查看登录状态
            try {
                password = AESUtils.encrypt(password, AESUtils.keySeed);
            } catch (Exception e) {
                System.err.println("prod_get_userinfo==>密码加密失败");
            }
            CASTicket t = CASClient.getTicketByAES(server + "/v1/tickets", username, password, service);
            String tgt = t.getTicketGrantingTicket();
            System.out.println("tgt:"+tgt);
            String st = t.getServiceTicket();
            System.out.println("st:" + st);
            String userInfo = t.getUserInfo();
            System.out.println("userInfo:"+userInfo);
            String getErrorMessage = t.getErrorMessage();
            System.out.println("getErrorMessage:"+getErrorMessage);
        } catch (Exception e) {
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }



    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/USER", method = {RequestMethod.POST,RequestMethod.GET})
    public ResultBodyBean USER() {
        System.out.println("进入USER:");
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        List<User> ulist=null;
        try {
            //身份服务
            IIdentityService ids = AdapterFactory.getIdentityService();
            String loginCode =  "30035976";  //ISCUtil.getISCUserInfoBean(request).getIscUserSourceId();
            ulist = ids.getUsersByLoginCode(loginCode);
            resultBodyBean.setData(ulist);
             System.out.println("通过用户登录账号取得用户信息：");
             System.out.println(JsonUtil.toJson(ulist));
        } catch (Exception e) {
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }



}
