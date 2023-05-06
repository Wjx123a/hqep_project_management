package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;

/**
 * @类名: PersonService
 * @功能描述 人员信息模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/18
 */
public interface PersonService {
    /**
     * @方法名:savePersonInfo
     * @功能描述:创建用户信息
     * @作者信息：Wang_XD
     * @创建时间:16:28 2019/9/18
     **/
    Integer savePersonInfo (PageData pd);
    /**
     * @方法名:boolenUsablePersonService
     * @功能描述:验证用户名是否存在
     * @作者信息：Wang_XD
     * @创建时间:16:05 2019/10/12
     **/
    boolean boolenUsablePersonService (String loginNum);

    /**
     * 查询用户列表
     * @param pd
     * @return
     */
    List<PageData> queryUserInfo(PageData pd) throws Exception;

    /**
     * 修改用户状态
     * @param pd
     */
    void updateUserStatus(PageData pd) throws Exception;

    /**
     * 重置密码123456
     * @param pd
     */
    void resetPassword(PageData pd) throws Exception;

    // 新建角色
    void insertRole(PageData pd) throws ServiceException;

    // 修改角色
    void updateRole(PageData pd) throws ServiceException;

    // 查询角色
    List<PageData> queryRole(PageData pd) throws ServiceException;

    // 查询单位
    List<PageData> queryCompany(PageData pd) throws ServiceException;
}
