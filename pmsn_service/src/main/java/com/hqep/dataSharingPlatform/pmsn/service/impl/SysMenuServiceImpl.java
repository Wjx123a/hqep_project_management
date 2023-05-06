package com.hqep.dataSharingPlatform.pmsn.service.impl;


import com.hqep.dataSharingPlatform.pmsn.dao.SysMenuDao;
import com.hqep.dataSharingPlatform.pmsn.service.SysMenuService;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao dao;

    /**
     * 查询共性数据集菜单
     * @param pd 点击的菜单级别，系统名称，上级的所有目录，用户id
     * @return 菜单的数据集合
     * @throws ServiceException
     */
    @Override
    public List<PageData> queryGxsjj(PageData pd) throws ServiceException {
        return dao.queryGxsjj(pd);
    }

    /**
     * 查询菜单
     * @param pd 点击的菜单级别，系统名称，上级的所有目录，用户id
     * @return 菜单的数据集合
     * @throws ServiceException
     */
    @Override
    public List<PageData> querySysMenus(PageData pd) throws ServiceException {
        try {
            return dao.querySysMenus(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 对用户进行授权
     * @param pd 用户id,菜单id
     * @throws ServiceException
     */
    @Override
    public void accredit(PageData pd) throws ServiceException{
        try {
            dao.accredit(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 对用户进行撤回授权
     * @param pd 用户id,菜单id
     * @throws ServiceException
     */
    @Override
    public void removeAccredit(PageData pd) throws ServiceException{
        try {
            dao.removeAccredit(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 取消查看菜单的权限
     * @param pd 用户id,菜单id
     * @throws ServiceException
     */
    @Override
    public void delAccredit(PageData pd) throws ServiceException {
        try {
            dao.delAccredit(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 查询所有使用的用户
     * @return 用户列表
     */
    @Override
    public List<PageData> queryUser (PageData pageData)throws ServiceException{
        try {
            return dao.queryUser(pageData);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 个修改用户状态
     * @param pd
     */
    @Override
    public void updateStatus(PageData pd) throws ServiceException{
        try {
            dao.updateStatus(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 查询无权限的菜单
     * @param pd
     */
    @Override
    public List<PageData> querySysMenuNoAuth(PageData pd) throws ServiceException{
        try {
            return dao.querySysMenuNoAuth(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 查询用户带权限的菜单id
     * @param pd
     */
    @Override
    public List<PageData> queryAuthMenuId(PageData pd) throws ServiceException {
        try {
            return dao.queryAuthMenuId(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> queryMenuId(PageData pd) throws ServiceException{
        try {
            return dao.queryMenuId(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


}
