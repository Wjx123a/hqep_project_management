package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import java.util.List;

public interface OrderStatisticsAnalysisService {


    /**
     * 全量工单统计分析 柱状图的数据查询
     */
    List<PageData> queryBarData(PageData pd);


    /**
     * 全量工单统计分析 表格的数据查询
     */
    List<PageData> queryTableListData(PageData pd);

    /**
     * 全量工单统计分析表格的详情查询
     */
    List<PageData> queryTableListDataForDetails(PageData pd);
}
