package com.hqep.dataSharingPlatform.isc.action;

import com.google.gson.GsonBuilder;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.isc.utils.ISCUtil_new;
import com.sgcc.isc.core.orm.complex.FunctionTree;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.isc.service.adapter.helper.IResourceService;
import com.sgcc.isc.service.adapter.utils.JsonUtil;
import com.sgcc.isc.ualogin.client.IscServiceTicketValidator;
import com.sgcc.isc.ualogin.client.util.IscSSOResourceUtil;
import com.sgcc.isc.ualogin.client.vo.IscSSOUserBean;
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

/**
 * @program: hqep_project_management
 * @ClassName iscTest
 * @author: sssJL
 * @create: 2022-09-26 13:47
 * @Version V1.0
 * @description:
 **/
@Controller
@RequestMapping("/isc")
public class iscTestController {

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @RequestMapping("/test")
    @ResponseBody
    public ResultBodyBean iscTest(@RequestBody PageData pd){

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
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
                    IResourceService rs = AdapterFactory.getResourceService();
                    System.out.println("rsrsrsrsrsrsrs"+rs);
                    String appId = "94c6880b80ee5570018166a411962b11";
                    FunctionTree ft = rs.getFuncTree(userid, appId, null);
                    System.out.println("资源服务-当前用户在当前系统下的菜单树："+ JsonUtil.toJson(ft));

                } else {
                    System.out.println("userinfo >>>>>>>>>>>> 用户信息获取失败");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            map = new HashMap();
            map.put("aaa","111");
            ISCUtil_new isc = new ISCUtil_new();
            System.out.println(isc);
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
