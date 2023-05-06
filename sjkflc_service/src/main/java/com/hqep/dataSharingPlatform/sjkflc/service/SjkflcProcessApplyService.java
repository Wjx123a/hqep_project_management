package com.hqep.dataSharingPlatform.sjkflc.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;

public interface SjkflcProcessApplyService {

    /**
     * 查询相关内容
     */

    // 查询申请去审批
    List<PageData> queryProgressToAudit(PageData pd) throws ServiceException;

    // 工单管理查询
    List<PageData> queryOrders(PageData pd) throws ServiceException;

    /**
     * 流程相关内容
     */

    //查询当前状态为提交申请的指定需求ID的流程信息（将第一步直接过滤）
    List<PageData> queryProgressBySqxqID(PageData pd) throws ServiceException;

    // 查询流程最新进度
    List<PageData> queryLatestProgress(PageData pd) throws ServiceException;

    // 插入流程最新进度
    void addProgress(PageData applyData);

    // 更新流程最新进度
    void editLatestProgress(PageData pd) throws ServiceException;

    // 插入流程审批日志
    void insertProcessLog(PageData pd) throws ServiceException;

    // 查询表名存在的流程ID及当前节点ID
    List<PageData> queryZxStatus(PageData pd) throws ServiceException;

    // 流程审批通过
    void auditProgressYes(PageData applyData);

    // 流程审批不通过
    void auditProgressNo(PageData applyData);

    // 流程审批撤回
    void auditProgressBack(PageData applyData);

    // 删除流程审批日志
    void delLcsprzb(PageData pd);

    List<PageData>  addNextNode( List<PageData> list);
}














