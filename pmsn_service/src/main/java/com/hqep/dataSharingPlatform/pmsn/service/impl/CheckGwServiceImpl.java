package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.CheckGwDao;
import com.hqep.dataSharingPlatform.pmsn.service.CheckGwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class CheckGwServiceImpl implements CheckGwService {

    @Autowired
    CheckGwDao dao;

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int insertCatalogTree(List<Map<String, Object>> list) {
        return dao.insertCatalogTree(list);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int insertTableList(List<Map<String, Object>> list) {
        return dao.insertTableList(list);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int insertTableProList(List<Map<String, Object>> list) {
        return dao.insertTableProList(list);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int insertCatalogTreeByOne(Map<String,Object> item) {
        return dao.insertCatalogTreeByOne(item);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int insertTableListByOne(Map<String,Object> item) {
        return dao.insertTableListByOne(item);
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public int insertTableProListByOne(Map<String,Object> item) {
        return dao.insertTableProListByOne(item);
    }

    /**
     * 获取国网的系统列表
     * @param pd
     * @return
     */
    @Override
    public List<PageData> queryGwSystemList(PageData pd) {
        return dao.queryGwSystemList(pd);
    }


}
