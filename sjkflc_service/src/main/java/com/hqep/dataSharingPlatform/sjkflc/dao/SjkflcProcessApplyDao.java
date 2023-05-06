package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流程申请
 */
@Repository
public interface SjkflcProcessApplyDao {

    /**
     * 查询相关内容
     */

    // 查询申请去审批
    List<PageData> queryProgressToAudit(PageData pd);

    // 工单管理查询
    List<PageData> queryOrders(PageData pd);

    /**
     * 流程相关内容
     */

    //查询当前状态为提交申请的指定需求ID的流程信息（将第一步直接过滤）
    List<PageData> queryProgressBySqxqID(PageData pd);

    // 查流程最新进度
    List<PageData> queryLatestProgress(PageData pd);

    // 插入流程最新进度
    void insertLatestProgress(PageData pd);

    // 更新流程最新进度
    void editLatestProgress(PageData pd);

    // 插入流程审批日志
    void insertProcessLog(PageData pd);

    // 查询表名存在的流程ID及当前节点ID
    List<PageData> queryZxStatus(PageData pd);

    // 查询表是否在负面清单内
    PageData isInBadList(PageData pd);

    // 删除流程最新进度
    void delLatestProgress(PageData pd);

    // 删除流程审批日志
    void delLcsprzb(PageData pd);

}












