package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 两级数据目录一致性校验方案
 */
@Repository
// 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
public interface CheckDao {


    /**
     * 查询资源目录树信息
     * @param pd
     * @return
     */
    List<Map<String,String>> queryCatalogTree(PageData pd);

    /**
     * 查询表列表信息
     * @param pd
     * @return
     */
    List<Map<String,String>> queryTableList(PageData pd);

    /**
     * 查询表列表信息 求和
     * @param pd
     * @return
     */
    int queryTableListCount(PageData pd);

    /**
     * 查询表属性信息
     * @param pd
     * @return
     */
    List<Map<String,String>> queryTableProList(PageData pd);



    /**
     * 查询表属性信息 求和
     * @param pd
     * @return
     */
    int queryTableProListCount(PageData pd);


    /**
     * 查询表属性信息
     * @param pd
     * @return
     */
    List<Map<String,String>> queryDept(PageData pd);


    List<PageData> queryt_dm_zb_catalog_num_zl(PageData pd);

    int queryt_dm_zb_catalog_num_count_zl(PageData pd);

    List<PageData> queryt_dm_zb_catalog_num_cl(PageData pd);

    int queryt_dm_zb_catalog_num_count_cl(PageData pd);

    List<PageData> queryt_t_dm_province_catalog_num_zl(PageData pd);

    int queryt_t_dm_province_catalog_num_count_zl(PageData pd);

}
