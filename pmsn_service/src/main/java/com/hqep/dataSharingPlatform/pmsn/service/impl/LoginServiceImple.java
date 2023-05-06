package com.hqep.dataSharingPlatform.pmsn.service.impl;


import com.google.gson.GsonBuilder;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.*;
import com.hqep.dataSharingPlatform.pmsn.service.LoginService;
import com.hqep.dataSharingPlatform.pmsn.dao.LoginDao;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.isc.core.orm.role.OrganizationalRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.MapObjUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @类名: LoginServiceImple
 * @功能描述 登陆模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/23
 */
@Service
public class LoginServiceImple implements LoginService {

    @Autowired
    private LoginDao loginDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String errorCode = "500";
    private String errorTest = "用户名或密码错误!";


    /**
     * @方法名:login
     * @功能描述:登陆系统
     * @作者信息：Wang_XD
     * @创建时间:14:14 2019/9/23
     **/
    @Override
    public ResultBodyBean loginService(PageData pd, HttpServletRequest request) {
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            ISCUtil iscUtil = new ISCUtil();
            // PageData loginInfo = loginDao.loginPersonInfoMapper(pd);
            String password = pd.getString("password");
            String loginNum = pd.getString("loginNum");
            // 查看登录状态
            String userInfo = iscUtil.prod_get_userinfo(loginNum,password,request);
            System.out.println("调用接口返回用户信息==》userInfo:"+userInfo);
            if(userInfo == null || "".equals(userInfo)) {
                errorReasonBean.setCode(errorCode);
                errorReasonBean.setText(errorTest);
                resultBodyBean.setError(errorReasonBean);
            } else {
                System.out.println("1111111");
                long ttlmillis = 1;
                Map userMap = new GsonBuilder().serializeNulls().create().fromJson(userInfo, Map.class);
                userMap.put("loginNum",userMap.get("iscUserSourceId"));
                userMap.put("personId",userMap.get("iscUserId"));
                userMap.put("id",userMap.get("iscUserId"));
                System.out.println("userMap:"+userMap);
                // 查找组织结构
                System.out.println("查找组织结构==========");
                System.out.println("22222");
                List<OrganizationalRole> orglist = iscUtil.getOrgRolesByUserId2();
                System.out.println("查找用户信息==========");
                List<User> ulist = iscUtil.getUsersByLoginCode(request);
                System.out.println("ulist:"+ulist);
                System.out.println("查找组织结构结果=========="+orglist);
                Map org = MapObjUtil.ObjectIntoMap(orglist.get(0),null);
                userMap.put("orgName", org.get("name"));
                userMap.put("orgCode", org.get("code"));
//                userMap.put("orgName", "本部");
//                userMap.put("orgCode", "2400");
                // 查询角色信息
                System.out.println("查询角色信息==========>");
                List<OrganizationalRole> roleLsit = iscUtil.getOrgRolesByUserId(request);
                System.out.println("查询角色信息结果=========="+roleLsit);
                System.out.println(roleLsit);
                if (roleLsit != null && roleLsit.size()>0) {
                    userMap.put("roleId","21"); //roleLsit.get(0).getRoleId());
                    userMap.put("roleName",roleLsit.get(0).getName());
                    userMap.put("roleInfo",roleLsit);
                } else {
                    userMap.put("roleId","");
                    userMap.put("roleName","");
                    userMap.put("roleInfo",new ArrayList());
                }
                // 信息字段未知 暂时写一个固定值
                userMap.put("phone","18246000000");
                if ("admin".equals(loginNum)) {
                    userMap.put("isAdmin","1");
                } else {
                    userMap.put("isAdmin","0");
                }
                System.out.println("LoginServiceImple==userMap:"+ userMap);
                PageData userPageData = new PageData(userMap);
                String[] time = ConfigUtil.getString("ttlmillis").split("\\*");
                for (int i = 0; i < time.length; i++) {
                    ttlmillis *= Long.valueOf(time[i]);
                }
                logger.info("ttlmillis-->>" + ttlmillis);
                String token = JwtUtil.createJWT(ConfigUtil.getString("issuer"), userPageData, ttlmillis);
                PageData pageData = new PageData();
                pageData.put("token", token);
                pageData.put("personInfo", userPageData);
                resultBodyBean.setData(pageData);
            }
        } catch (Exception e) {
            System.out.println("333333");
            e.printStackTrace();
        }
        System.out.println("LoginServiceImple==>resultBodyBean:"+ resultBodyBean);
        return resultBodyBean;
    }




    /**
     * @方法名:login
     * @功能描述:登陆系统
     * @作者信息：Wang_XD
     * @创建时间:14:14 2019/9/23
     **/
    @DataSource(name=DataSource.DATA_SOURCE_1)
    @Override
    public ResultBodyBean loginService(PageData pd) {
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        PageData loginInfo = loginDao.loginPersonInfoMapper(pd);
        long ttlmillis = 1;
        if (loginInfo == null) {
            errorReasonBean.setCode(errorCode);
            errorReasonBean.setText(errorTest);
            resultBodyBean.setError(errorReasonBean);
        } else {
            if ("0".equals(loginInfo.getString("status"))) {
                errorReasonBean.setCode(errorCode);
                errorReasonBean.setText("用户不可用!");
                resultBodyBean.setError(errorReasonBean);
            } else {
                if (!pd.getString("password").equals(loginInfo.getString("password"))) {
                    errorReasonBean.setCode(errorCode);
                    errorReasonBean.setText(errorTest);
                    resultBodyBean.setError(errorReasonBean);
                } else {
                    String[] time = ConfigUtil.getString("ttlmillis").split("\\*");
                    for (int i = 0; i < time.length; i++) {
                        ttlmillis *= Long.valueOf(time[i]);
                    }
                    logger.info("ttlmillis-->>" + ttlmillis);
                    String token = JwtUtil.createJWT(ConfigUtil.getString("issuer"), loginInfo, ttlmillis);
//                    System.out.println("token"+token);
//                    System.out.println("token"+token);
//                    System.out.println("token"+token);
                    PageData pageData = new PageData();
                    loginInfo.put("password", null);
                    loginInfo.put("isIsc", null);
                    if (pd.get("iscUserName")!= null && !"".equals(pd.get("iscUserName"))) {
                        loginInfo.put("loginNum", pd.get("iscUserName"));
                        loginInfo.put("isIsc",  pd.get("iscUserId"));
                    }
                    pageData.put("token", token);
                    pageData.put("personInfo", loginInfo);
                    resultBodyBean.setData(pageData);
                }
            }
        }
        return resultBodyBean;
    }


    /**
     * @方法名:updatePasswordService
     * @功能描述:修改密码
     * @作者信息：Wang_XD
     * @创建时间:16:14 2019/9/23
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public ResultBodyBean updatePasswordService(PageData pd) throws Exception {
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        String oldPassword = pd.getString("password");
        String newPassword = pd.getString("newPassword");
        PageData personInfo = loginDao.loginPersonInfoMapper(pd);
        if (personInfo != null) {
            if (!oldPassword.equals(personInfo.getString("password"))) {
                errorReasonBean.setCode("500");
                errorReasonBean.setText("原密码输入错误!");
                resultBodyBean.setError(errorReasonBean);
            } else if (oldPassword.equals(newPassword)) {
                errorReasonBean.setCode("500");
                errorReasonBean.setText("新密码不能与就密码相同!");
                resultBodyBean.setError(errorReasonBean);
            } else {
                Integer update = loginDao.updatePasswordMapper(pd);
                if (update > 0) {
                    resultBodyBean.setData("密码修改成功!");
                } else {
                    errorReasonBean.setCode("500");
                    errorReasonBean.setText("修改失败");
                    resultBodyBean.setError(errorReasonBean);
                }
            }
        } else {
            errorReasonBean.setCode(errorCode);
            errorReasonBean.setText(errorTest);
            resultBodyBean.setError(errorReasonBean);
        }
        return resultBodyBean;
    }

    @Override
    public void resetPassword(PageData pd) {
        loginDao.resetPassword(pd);

    }

    /**
     * 初始化权限
     * @param pd
     */
    @Override
    public void firstMenuAuth(PageData pd) {
        loginDao.firstMenuAuth(pd);
    }


    public static void main(String[] args) {
        long time = 1;
        String[] reflash = ConfigUtil.getString("ttlmillis").split("\\*");
        for (int i = 0; i < reflash.length; i++) {
            time *= Long.parseLong(reflash[i]);
            System.out.println(Long.parseLong(reflash[i]));
            System.out.println("time-->>" + time);
        }
    }
}

