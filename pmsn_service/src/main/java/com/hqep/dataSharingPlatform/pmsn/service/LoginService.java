package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.servlet.http.HttpServletRequest;

/**
 * @类名: LoginService
 * @功能描述 登陆模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/23
 */
public interface LoginService {


    /**
     * @方法名:login
     * @功能描述:登陆系统
     * @作者信息：Wang_XD
     * @创建时间:14:14 2019/9/23
     **/
    public ResultBodyBean loginService(PageData pd, HttpServletRequest request);

    /**
     * @方法名:login
     * @功能描述:登陆系统
     * @作者信息：Wang_XD
     * @创建时间:9:13 2019/9/23
     **/
    public ResultBodyBean loginService(PageData pd);

    /**
     * @方法名:updatePasswordService
     * @功能描述:修改密码
     * @作者信息：Wang_XD
     * @创建时间:16:14 2019/9/23
     **/
    public ResultBodyBean updatePasswordService(PageData pd) throws Exception;

    /**
     * 重置密码
     * @param pd
     */
    void resetPassword(PageData pd);

    /**
     * 初始化权限
     * @param pd
     */
    void firstMenuAuth(PageData pd);
}
