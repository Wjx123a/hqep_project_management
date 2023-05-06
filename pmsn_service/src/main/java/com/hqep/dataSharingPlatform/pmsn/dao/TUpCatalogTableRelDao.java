package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (TUpCatalogTableRel)表数据库访问层
 *
 * @author sssJL
 * @since 2022-02-25 15:30:50
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface TUpCatalogTableRelDao {


    /**
     * 一级目录菜单查询
     * @param pd 实例对象
     * @return 对象列表
     */
    List<PageData> queryOneLevelList(PageData pd);
    /**
     * 二级目录菜单查询
     * @param pd 实例对象
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

