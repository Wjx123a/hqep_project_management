package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;

public interface SysMenuService {
    //查询共性数据集菜单
    List<PageData> queryGxsjj(PageData pd) throws ServiceException;
    //查询菜单
    List<PageData> querySysMenus(PageData pd) throws ServiceException;

    //对用户进行授权（查看相应的菜单）
    void accredit(PageData pd) throws ServiceException;

    //对用户进行撤回授权（查看相应的菜单）
    void removeAccredit(PageData pd) throws ServiceException;

    //取消查看菜单的权限
    void delAccredit(PageData pd) throws ServiceException;

    //查询所有的用户
    List<PageData> queryUser(PageData pd) throws ServiceException;

    //修改用户状态
    void updateStatus(PageData pd) throws ServiceException;

    //查询无权限的菜单
    List<PageData> querySysMenuNoAuth(PageData pd) throws ServiceException;

    //查询用户带权限的菜单id
    List<PageData> queryAuthMenuId(PageData pd) throws ServiceException;

    List<String> queryMenuId(PageData pd) throws ServiceException;
}
