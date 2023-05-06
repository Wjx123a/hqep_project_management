package com.hqep.dataSharingPlatform.pmsn.action;

import com.google.gson.GsonBuilder;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.pmsn.service.LoginService;
import com.hqep.dataSharingPlatform.pmsn.service.PersonService;
import com.sgcc.isc.core.orm.complex.FunctionTree;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.isc.service.adapter.helper.IResourceService;
import com.sgcc.isc.service.adapter.utils.JsonUtil;
import com.sgcc.isc.ualogin.client.IscServiceTicketValidator;
import com.sgcc.isc.ualogin.client.vo.IscSSOUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: hqep_project_management
 * @ClassName iscTest
 * @author: sssJL
 * @create: 2022-09-26 13:47
 * @Version V1.0
 * @description:
 **/
@Controller
@RequestMapping("/iscService")
public class IscServiceController {

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;


    @Autowired
    private PersonService personService;
    @Autowired
    protected LoginService loginService;


    @RequestMapping("/shortLogin")
    @ResponseBody
    @RequestLog("登陆系统")
    public ResultBodyBean shortLogin(@RequestBody PageData pd){

        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        Map map = null;
        try {
            IscServiceTicketValidator  sv = new IscServiceTicketValidator();
            sv.setCasValidateUrl("http://10.166.3.29:7111/isc_sso/serviceValidate");
            /*业务系统LoginModule访问地址*/
            sv.setService("https://10.166.8.157:13000/");
            /*设置Ticket*/
            sv.setServiceTicket((String) pd.get("ticket"));
            try {
                sv.validate();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("认证失败");
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("认证失败2");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("认证失败3");
            }
            System.out.println("认证成功");
            String user = null;
            if(sv.isAuthenticationSuccesful()) {
                user = sv.getUser();
            } else {
                String errorCode = sv.getErrorCode();
                String errorMessage = sv.getErrorMessage();
                /* handle the error */
                System.out.println("errorInfo -----------> "+errorCode +"\r\n"+errorMessage);
            }
            System.out.println("userinfo >>>>>>>>>>>> "+user);
            IscSSOUserBean iscSSOUserBean = null;
            try {
                if (user != null) {
                    System.out.println("输出用户信息");
                    /*获取当前用户登录信息*/
                    String cd = URLDecoder.decode(user, "UTF-8");
                    System.out.println("输出用户信息转义cd"+cd);
                    iscSSOUserBean = new GsonBuilder().serializeNulls().create().fromJson(cd, IscSSOUserBean.class);

                    System.out.println("输出用户转义fromJson:"+iscSSOUserBean);
                    /*当前登录用户ID*/
                    String userid = iscSSOUserBean.getIscUserId();
                    System.out.println("userid："+userid);
                    /*当前登录用户账号*/
                    String loginName = iscSSOUserBean.getIscUserSourceId();
                    System.out.println("loginName:" + loginName);

                    //资源服务
//                    IResourceService rs = AdapterFactory.getResourceService();
//                    System.out.println("rsrsrsrsrsrsrs"+rs);
//                    String appId = "94c6880b80ee5570018166a411962b11";
//                    FunctionTree ft = rs.getFuncTree(userid, appId, null);
//                    System.out.println("资源服务-当前用户在当前系统下的菜单树："+ JsonUtil.toJson(ft));

                    Map result = new GsonBuilder().serializeNulls().create().fromJson(cd, Map.class);
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
//                        NR_REST_API = "http://10.166.8.157:8081/isctools/isc/userBean";
                        map = new HashMap();
                        map.put("iscUserId", (String) result.get("iscUserId"));
                        System.out.println("=========进入isc跳转=》调用isc接口=======================");
//                        String allInfo = new RestfulHttpClient().getRestApiForAll(map,NR_REST_API);
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
                } else {
                    System.out.println("userinfo >>>>>>>>>>>> 用户信息获取失败");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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




}
