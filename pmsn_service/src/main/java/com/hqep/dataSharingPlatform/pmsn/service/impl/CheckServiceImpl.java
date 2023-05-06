package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.CheckDao;
import com.hqep.dataSharingPlatform.pmsn.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 两级数据目录一致性校验方案
 */
@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    CheckDao dao;

    /**
     * 查询资源目录树信息
     * @param pd
     * @return
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    public List<Map<String,String>> queryCatalogTree(PageData pd) {
        return dao.queryCatalogTree(pd);
    }

    /**
     * 查询表列表信息
     * @param pd
     * @return
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    public List<Map<String,String>> queryTableList(PageData pd) {
        return dao.queryTableList(pd);

    }
    /**
     * 查询表列表信息 求和
     * @param pd
     * @return
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    public int queryTableListCount(PageData pd) {
        return dao.queryTableListCount(pd);

    }


    /**
     * 查询表属性信息
     * @param pd
     * @return
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    public List<Map<String,String>> queryTableProList(PageData pd){
        return dao.queryTableProList(pd);

    }

    /**
     * 查询表属性信息 求和
     * @param pd
     * @return
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    public int queryTableProListCount(PageData pd) {
        return dao.queryTableProListCount(pd);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    public List<Map<String,String>> queryDept(PageData pd) {
        return dao.queryDept(pd);
    }


    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public List<PageData>  queryt_dm_zb_catalog_num_zl(PageData pd) {
        return  dao.queryt_dm_zb_catalog_num_zl(pd);
    }
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int  queryt_dm_zb_catalog_num_count_zl(PageData pd) {
        return  dao.queryt_dm_zb_catalog_num_count_zl(pd);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public List<PageData>  queryt_dm_zb_catalog_num_cl(PageData pd) {
        return  dao.queryt_dm_zb_catalog_num_cl(pd);
    }
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int  queryt_dm_zb_catalog_num_count_cl(PageData pd) {
        return  dao.queryt_dm_zb_catalog_num_count_cl(pd);
    }



    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public List<PageData>  queryt_t_dm_province_catalog_num_zl(PageData pd) {
        return  dao.queryt_t_dm_province_catalog_num_zl(pd);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int  queryt_t_dm_province_catalog_num_count_zl(PageData pd) {
        return  dao.queryt_t_dm_province_catalog_num_count_zl(pd);
    }

}
