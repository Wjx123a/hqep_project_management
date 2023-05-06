package com.hqep.dataSharingPlatform.pmsn.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.PageData;

import java.util.List;

/**
 * (TUpCatalogTableRel)表服务接口
 *
 * @author sssJL
 * @since 2022-02-25 15:30:49
 */
public interface TUpCatalogTableRelService {


    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<PageData> queryOneLevelList(PageData pd);
    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<PageData> queryTwoLevelList(PageData pd);

    /**
     * 一级目录数据查询
     * @param pd 实例对象
     * @return 对象列表
     */
    List<PageData> queryOneLevelListByOneSystem(PageData pd);

    /**
     * 二级目录数据查询
     * @param pd 实例对象
     * @return 对象列表
     */
    List<PageData> queryOneLevelListByTwoSystem(PageData pd);


    /**
     * 数据中台 => 中台贴源层 => 菜单查询
     * @param pd 实例对象
     * @return 对象列表
     */
    List<PageData> queryTycMenu(PageData pd);


    /**
     * 数据中台 => 中台贴源层 => 菜单查询 最后一级
     * @param pd 实例对象
     * @return 对象列表
     */
    List<PageData> queryTycMenuForLast(PageData pd);
}
