package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单查询
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface SysMenuDao {

    //查询共性数据集菜单
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryGxsjj(PageData pd);

    //查询目录
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> querySysMenus(PageData pd);

    //对用户进行授权
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void accredit(PageData pd);

    //对用户进行撤回授权
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void removeAccredit(PageData pd);

    //取消户查看菜单的权限
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void delAccredit(PageData pd);

    //查询所有使用的用户
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryUser(PageData pd);

    //修改用户状态
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void updateStatus(PageData pd);

    //无权限的菜单
    List<PageData> querySysMenuNoAuth(PageData pd);

    //查询用户带权限的菜单id
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryAuthMenuId(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<String> queryMenuId(PageData pd);
}