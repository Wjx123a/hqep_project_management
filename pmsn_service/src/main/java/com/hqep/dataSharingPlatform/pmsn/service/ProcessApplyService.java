package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

public interface ProcessApplyService {

    /**
     * 查询相关内容
     */

    //查询标签
    List<PageData> queryBq(PageData pd) throws ServiceException;

    // 查询有效表信息,申请表如果是负面清单就走子流程，如果不是负面清单就走主流程
    List<PageData> queryTableList(PageData pd) throws ServiceException;

    // 根据表名查询所有字段信息
    List<PageData> queryCloumnList(PageData pd) throws ServiceException;

    // 查询申请去审批
    List<PageData> queryProgressToAudit(PageData pd) throws ServiceException;

    // 用户查询自己提交的申请
    List<PageData> queryProgressListForMe(PageData pd) throws ServiceException;

    // 用户查询自己提交的申请的审批日志记录
    List<PageData> queryProgressListLogForMe(PageData pd) throws ServiceException;

    // 审批角色查询自己审批过的申请
    List<PageData> queryProgressListForAudit(PageData pd) throws ServiceException;

    // 查询有效表信息总条数
    int queryTableListCount(PageData pd) throws ServiceException;

    /**
     * 流程相关内容
     */

    // 查询流程最新进度
    List<PageData> queryLatestProgress(PageData pd) throws ServiceException;

    // 查询负面清单零审批的流程是否启用
    List<PageData> queryZeroState(PageData pd) throws ServiceException;

    // 插入流程最新进度
    void addProgress(PageData applyData);

    // 更新流程最新进度
    void editLatestProgress(PageData pd) throws ServiceException;

    // 插入流程审批日志
    void insertProcessLog(PageData pd) throws ServiceException;

    // 查询表名存在的流程ID及当前节点ID
    List<PageData> queryZxStatus(PageData pd) throws ServiceException;

    // 流程审批通过
    Map auditProgressYes(PageData applyData);
    //更新购物车提出申请状态0或1（提交为1，未提交为0）
    void updateshoppingcat(PageData applyData);

    //更新购物车（撤回为-2）
    void updateshoppingcatForRevoke(PageData applyData);

    // 流程审批不通过
    void auditProgressNo(PageData applyData);

    /**
     * 暂时没用
     */

    // 判断流程是否再进行中
    int queryStatus(PageData pd) throws ServiceException;

    // 查询当前启用流程的最后一级审批节点ID
    String queryLatestNodeId() throws ServiceException;

    /**
     * 20210302第二次修改
     */

    // 将待申请信息保存至购物车
    void insertDataToShoppingcat(PageData pd) throws ServiceException;

    // 根据登录人ID查询购物车信息
    List<PageData> queryShoppingcat(PageData pd) throws ServiceException;

    // 查询所有工单信息
    List<PageData> queryAllApplyDataList(PageData pd) throws ServiceException;

    // 查询工单信息下的申请子表
    List<PageData> queryAllApplyDataListForNext(PageData pd) throws ServiceException;

    // 查询工单流程审批详情信息
    List<PageData> queryAllApplyDataListForDetails(PageData pd) throws ServiceException;

    // 查询中台授权用户信息
    List<PageData> querySjztUserList(PageData pd) throws ServiceException;

    PageData querySpjb(PageData pd);

    /**
     * 移除购物车记录内容
     * @param pd
     * @return
     */
    boolean delForShoppingcat(PageData pd);


    /**
     * 根据本部授权表查询地市授权表信息
     * @param pd
     * @return
     */
    List<PageData> querySqTableName(PageData pd);
}














