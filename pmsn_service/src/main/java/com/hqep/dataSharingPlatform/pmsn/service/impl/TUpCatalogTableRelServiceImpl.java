package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import com.hqep.dataSharingPlatform.pmsn.dao.TUpCatalogTableRelDao;
import com.hqep.dataSharingPlatform.pmsn.service.TUpCatalogTableRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TUpCatalogTableRel)表服务实现类
 *
 * @author sssJL
 * @since 2022-02-25 15:30:50
 */
@Service
public class TUpCatalogTableRelServiceImpl implements TUpCatalogTableRelService {
    @Autowired
    private TUpCatalogTableRelDao tUpCatalogTableRelDao;

    /**
     * 查询多条数据
     *
     * @param pd 实例对象
     * @return 对象列表
     */
    @Override
    public List<PageData> queryOneLevelList(PageData pd) {
        return tUpCatalogTableRelDao.queryOneLevelList(pd);
    }

    /**
     * 查询多条数据
     *
     * @param pd 实例对象
     * @return 对象列表
     */
    @Override
    public List<PageData> queryTwoLevelList(PageData pd) {
        return tUpCatalogTableRelDao.queryTwoLevelList(pd);
    }

    /**
     * 一级目录数据查询
     * @param pd 实例对象
     * @return 对象列表
     */
    @Override
    public List<PageData> queryOneLevelListByOneSystem(PageData pd) {
        return tUpCatalogTableRelDao.queryOneLevelListByOneSystem(pd);
    }


    /**
     * 二级目录数据查询
     * @param pd 实例对象
     * @return 对象列表
     */
    @Override
    public List<PageData> queryOneLevelListByTwoSystem(PageData pd) {
        return tUpCatalogTableRelDao.queryOneLevelListByTwoSystem(pd);
    }

    /**
     * 数据中台 => 中台贴源层 => 菜单查询
     * @param pd 实例对象
     * @return 对象列表
     */
    @Override
    public List<PageData> queryTycMenu(PageData pd) {
        return tUpCatalogTableRelDao.queryTycMenu(pd);
    }

    /**
     * 数据中台 => 中台贴源层 => 菜单查询 最后一级
     * @param pd 实例对象
     * @return 对象列表
     */
    @Override
    public List<PageData> queryTycMenuForLast(PageData pd) {
        return tUpCatalogTableRelDao.queryTycMenuForLast(pd);
    }

}
