package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 数据共享流程运营监测 功能数据持久层
 */
@Repository
@sssjlDataSource(name= sssjlDataSource.DATA_SOURCE_1)
public interface SjgxlcyyjkDao {

    /**
     * 数据共享流程运营监测_环节总览_环节工单情况
     * @param pd
     * @return
     */
    List<Map> queryLinkOverview(PageData pd);

    /**
     * 数据共享流程运营监测_规模分析_需求受理情况and工单受理情况
     * @param pd
     * @return
     */
    List<Map> queryScaleAnalysisOrderNum(PageData pd);

    /**
     * 数据共享流程运营监测_规模分析_省公司工单申请情况统计
     * @param pd
     * @return
     */
    List<Map> queryScaleAnalysisOrderTypeForProvince(PageData pd);

    /**
     * 数据共享流程运营监测_规模分析_地市工单申请情况统计
     * @param pd
     * @return
     */
    List<Map> queryScaleAnalysisOrderTypeForCity(PageData pd);

    /**
     * 数据共享流程运营监测_规模分析_需求受理情况统计
     * @param pd
     * @return
     */
    List<Map> queryScaleAnalysisOrderMonth(PageData pd);


    /**
     * 数据共享流程运营监测_效率分析_全流程流转办理时长
     * @param pd
     * @return
     */
    List<Map> queryFlowTimeForType(PageData pd);
    /**
     * 数据共享流程运营监测_效率分析_各流转环节办理时长情况
     * @param pd
     * @return
     */
    LinkedList<Map> queryFlowTimeForNode(PageData pd);
    /**
     * 数据共享流程运营监测_效率分析_工单全流程流转时长月度趋势情况
     * @param pd
     * @return
     */
    List<Map> queryFlowTimeForMonth(PageData pd);
    /**
     * 数据共享流程运营监测_效率分析_工单全流程流转时长按单位统计
     * @param pd
     * @return
     */
    List<Map> queryFlowTimeForCity(PageData pd);
    /**
     * 数据共享流程运营监测_效率分析_业务审核环节审批时长分布
     * @param pd
     * @return
     */
    List<Map> queryFlowTimeForBusiness(PageData pd);


    /**
     * 数据共享流程运营监测_热度分析_热点系统
     * @param pd
     * @return
     */
    List<Map> queryDegreeHeatForSystem(PageData pd);


    /**
     * 数据共享流程运营监测_热度分析_负面清单热点数据表
     * @param pd
     * @return
     */
    List<Map> queryDegreeHeatForFmqdTable(PageData pd);


    /**
     * 数据共享流程运营监测_热度分析_非负面清单热点数据表
     * @param pd
     * @return
     */
    List<Map> queryDegreeHeatForNotFmqdTable(PageData pd);

    /**
     * 数据共享流程运营监测_质量分析_省公司被驳回
     * @param pd
     * @return
     */
    List<Map> queryQualityForProvince(PageData pd);

    /**
     * 数据共享流程运营监测_质量分析_地市公司被驳回
     * @param pd
     * @return
     */
    List<Map> queryQualityForCity(PageData pd);
    /**
     * 数据共享流程运营监测_预警异动_预警工单数据统计
     * @param pd
     * @return
     */
    List<Map> queryWarningForWarning(PageData pd);

    /**
     * 数据共享流程运营监测_预警异动_异动工单数据统计
     * @param pd
     * @return
     */
    List<Map> queryWarningForException(PageData pd);



}
