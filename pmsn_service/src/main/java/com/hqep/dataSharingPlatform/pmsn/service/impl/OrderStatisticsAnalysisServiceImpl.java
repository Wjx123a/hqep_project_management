package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.OrderStatisticsAnalysisDao;
import com.hqep.dataSharingPlatform.pmsn.service.OrderStatisticsAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderStatisticsAnalysisServiceImpl implements OrderStatisticsAnalysisService {

    @Autowired
    OrderStatisticsAnalysisDao orderStatisticsAnalysisDao;

    /**
     * 全量工单统计分析 柱状图的数据查询
     */
    @Override
    public List<PageData> queryBarData(PageData pd) {
        return orderStatisticsAnalysisDao.queryBarData(pd);
    }

    /**
     * 全量工单统计分析 表格的数据查询
     */
    @Override
    public List<PageData> queryTableListData(PageData pd) {
        return orderStatisticsAnalysisDao.queryTableListData(pd);
    }

    /**
     * 全量工单统计分析表格的详情查询
     */
    @Override
    public List<PageData> queryTableListDataForDetails(PageData pd) {
        return orderStatisticsAnalysisDao.queryTableListDataForDetails(pd);
    }
}
