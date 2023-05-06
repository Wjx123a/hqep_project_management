package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import java.util.List;
import java.util.Map;

/**
 * 两级数据目录一致性校验方案
 */
public interface CheckGwService {
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
    List<PageData> queryGwSystemList(PageData pd);

}
