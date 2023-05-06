package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface OrderStatisticsAnalysisDao {

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
