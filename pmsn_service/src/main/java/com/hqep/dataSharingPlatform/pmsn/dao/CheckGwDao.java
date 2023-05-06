package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
// 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
public interface CheckGwDao {
    int insertCatalogTree(List<Map<String,Object>> list);
    int insertTableList(List<Map<String,Object>> list);
    int insertTableProList(List<Map<String,Object>> list);
    int insertCatalogTreeByOne(Map<String,Object> item);
    int insertTableListByOne(Map<String,Object> item);
    int insertTableProListByOne(Map<String,Object> item);

    /**
     * 获取国网的系统列表
     * @param pd
     * @return
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    List<PageData> queryGwSystemList(PageData pd);

}
