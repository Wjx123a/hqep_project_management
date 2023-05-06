package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.SjgxlcyyjkDao;
import com.hqep.dataSharingPlatform.pmsn.service.SjgxlcyyjkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 数据共享流程运营监测 业务逻辑层
 */
@Service
public class SjgxlcyyjkServiceImpl implements SjgxlcyyjkService {

    @Autowired
    private SjgxlcyyjkDao dao;

    /**
     * 数据共享流程运营监测_环节总览_环节工单情况
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryLinkOverview(PageData pd) {
        return dao.queryLinkOverview(pd);
    }

    /**
     * 数据共享流程运营监测_规模分析_需求受理情况and工单受理情况
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryScaleAnalysisOrderNum(PageData pd) {
        return dao.queryScaleAnalysisOrderNum(pd);
    }

    /**
     * 数据共享流程运营监测_规模分析_省公司工单申请情况统计
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryScaleAnalysisOrderTypeForProvince(PageData pd) {
        return dao.queryScaleAnalysisOrderTypeForProvince(pd);
    }

    /**
     * 数据共享流程运营监测_规模分析_地市工单申请情况统计
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryScaleAnalysisOrderTypeForCity(PageData pd) {
        return dao.queryScaleAnalysisOrderTypeForCity(pd);
    }

    /**
     * 数据共享流程运营监测_规模分析_需求受理情况统计
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryScaleAnalysisOrderMonth(PageData pd) {
        return dao.queryScaleAnalysisOrderMonth(pd);
    }


    /**
     * 数据共享流程运营监测_效率分析_全流程流转办理时长
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryFlowTimeForType(PageData pd) {
        return dao.queryFlowTimeForType(pd);
    }
    /**
     * 数据共享流程运营监测_效率分析_各流转环节办理时长情况
     * @param pd
     * @return
     */
    @Override
    public LinkedList<Map> queryFlowTimeForNode(PageData pd) {
        return dao.queryFlowTimeForNode(pd);
    }
    /**
     * 数据共享流程运营监测_效率分析_工单全流程流转时长月度趋势情况
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryFlowTimeForMonth(PageData pd) {
        return dao.queryFlowTimeForMonth(pd);
    }
    /**
     * 数据共享流程运营监测_效率分析_工单全流程流转时长按单位统计
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryFlowTimeForCity(PageData pd) {
        return dao.queryFlowTimeForCity(pd);
    }
    /**
     * 数据共享流程运营监测_效率分析_业务审核环节审批时长分布
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryFlowTimeForBusiness(PageData pd) {
        return dao.queryFlowTimeForBusiness(pd);
    }
    /**
     * 数据共享流程运营监测_热度分析_热点系统
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryDegreeHeatForSystem(PageData pd) {
        return dao.queryDegreeHeatForSystem(pd);
    }

    /**
     * 数据共享流程运营监测_热度分析_负面清单热点数据表
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryDegreeHeatForFmqdTable(PageData pd) {
        return dao.queryDegreeHeatForFmqdTable(pd);
    }
    /**
     * 数据共享流程运营监测_热度分析_非负面清单热点数据表
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryDegreeHeatForNotFmqdTable(PageData pd) {
        return dao.queryDegreeHeatForNotFmqdTable(pd);
    }

    /**
     * 数据共享流程运营监测_质量分析_省公司被驳回
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryQualityForProvince(PageData pd) {
        return dao.queryQualityForProvince(pd);
    }
    /**
     * 数据共享流程运营监测_质量分析_地市公司被驳回
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryQualityForCity(PageData pd) {
        return dao.queryQualityForCity(pd);
    }
    /**
     * 数据共享流程运营监测_预警异动_预警工单数据统计
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryWarningForWarning(PageData pd) {
        return dao.queryWarningForWarning(pd);
    }

    /**
     * 数据共享流程运营监测_预警异动_异动工单数据统计
     * @param pd
     * @return
     */
    @Override
    public List<Map> queryWarningForException(PageData pd) {
        return dao.queryWarningForException(pd);
    }
}
