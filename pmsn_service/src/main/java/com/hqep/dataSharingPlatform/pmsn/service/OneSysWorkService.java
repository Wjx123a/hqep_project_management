package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

public interface OneSysWorkService {

    // 将待申请信息保存至订单栏
    int insertDataToShoppingcatOnesys(PageData pd) throws ServiceException;

    // 通过主键修改数据-订单栏
    int updateDataToShoppingcatOnesys(PageData pd) throws ServiceException;

    // 通过主键删除数据-订单栏
    int delShoppingcatOnesys(PageData pd) throws ServiceException;

    // 根据登录人ID查询订单栏信息
    List<PageData> queryShoppingcatOnesys(PageData pd) throws ServiceException;

    // 查询提交订单信息的信息
    List<PageData> queryDataToOnesysWorkOrderList(PageData pd) throws ServiceException;

    // 查询提交国网订单子表信息
    List<PageData> queryDataToOnesysWorkTablesList(PageData pd) throws ServiceException;

    // 查询提交国网的订单信息的信息（只查询上传国网的相应字段)
    List<Map<String, Object>> queryListOnesysWorkOrderForGwFields(PageData pd) throws ServiceException;

    // 查询提交国网订单子表信息（只查询上传国网的相应字段)
    List<Map<String, Object>> queryListOnesysWorkTablesForGwFields(PageData pd) throws ServiceException;

    // 将待申请信息保存至网省创建订单表
    int insertDataToOnesysWorkOrder(PageData pd) throws ServiceException;

    // 将待申请信息保存至网省创建订单子表
    int insertDataToOnesysWorkTables(Map map) throws ServiceException;

    // 将待申请信息保存至网省上传附件表
    int insertDataToOnesysFiles(PageData pd) throws ServiceException;

    // 通过主键修改数据-网省创建订单表
    int updateDataToOnesysWorkOrder(PageData pd) throws ServiceException;

    // 通过主键修改数据-网省创建订单子表
    int updateDataToOnesysWorkTables(PageData pd) throws ServiceException;

    // 通过主键修改数据-网省上传附件表
    int updateDataToOnesysWorkFiles(PageData pd) throws ServiceException;
    
    // 通过主键删除数据-网省创建订单表
    int deleteByOnesysWorkOrderId(PageData pd) throws ServiceException;
    // 通过主键删除数据-网省创建订单表
    int deleteByOnesysWorkOrderShoppingcatId(PageData pd) throws ServiceException;

    // 通过主键删除数据-网省创建订单子表
    int deleteByOnesysWorkTablesId(PageData pd) throws ServiceException;

    // 通过主键删除数据-网省上传附件表
    int deleteByOnesysWorkFilesId(PageData pd) throws ServiceException;


    // 查询一级数据下发日志信息
    List<PageData> queryDataToOnesysDownLogsList(PageData pd) throws ServiceException;
    // 查询一级数据下发附件信息
    List<PageData> queryOnesysDownFilesList(PageData pd) throws ServiceException;
    // 查询一级系统下发申请工单
    List<PageData> queryOnesysDownOrderList(PageData pd) throws ServiceException;
    // 查询一级系统下发申请工单子表
    List<PageData> queryOnesysDownTableList(PageData pd) throws ServiceException;
    // 查询一级系统下发审批流程节点
    List<PageData> queryOnesysDownActList(PageData pd) throws ServiceException;

    // 新增一级数据下发日志信息
    int insertOnesysDownLogs(PageData pd);
    // 新增一级数据下发附件信息
    int insertOnesysDownFiles(PageData pd) throws ServiceException;
    // 新增一级系统下发申请工单
    int insertOnesysDownOrder(PageData pd) throws ServiceException;
    // 新增一级系统下发申请工单子表
    int insertOnesysDownTable(Map map) throws ServiceException;
    // 新增一级系统下发审批流程节点
    int insertOnesysDownAct(PageData pd) throws ServiceException;


}
