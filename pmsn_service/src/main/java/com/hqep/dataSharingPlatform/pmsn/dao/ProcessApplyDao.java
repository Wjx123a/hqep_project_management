package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流程申请
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface ProcessApplyDao {

    /**
     * 查询相关内容
     */

    //查询标签
    List<PageData> queryBq(PageData pd);

    // 查询有效表信息,申请表如果是负面清单就走子流程，如果不是负面清单就走主流程
    List<PageData> queryTableList(PageData pd);

    // 根据表名查询所有字段信息
    List<PageData> queryCloumnList(PageData pd);

    // 查询申请去审批
    List<PageData> queryProgressToAudit(PageData pd);

    // 用户查询自己提交的申请
    List<PageData> queryProgressListForMe(PageData pd);

    // 用户查询自己提交的申请的审批日志记录
    List<PageData> queryProgressListLogForMe(PageData pd);

    // 审批角色查询自己审批过的申请
    List<PageData> queryProgressListForAudit(PageData pd);

    // 查询有效表信息总条数
    int queryTableListCount(PageData pd);

    /**
     * 流程相关内容
     */

    // 查流程最新进度
    List<PageData> queryLatestProgress(PageData pd);

    // 查询负面清单零审批的流程是否启用
    List<PageData> queryZeroState(PageData pd);

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

    /**
     * 暂时没用
     */

    // 判断流程是否再进行中
    int queryStatus(PageData pd);

    // 查询当前启用流程的最后一级审批节点ID
    String queryLatestNodeId();

    /**
     * 20210302第二次修改
     */

    // 将待申请信息保存至购物车
    void insertDataToShoppingcat(PageData pd);

    // 根据登录人ID查询购物车信息
    List<PageData> queryShoppingcat(PageData pd);

    // 查询工单信息下的申请子表
    List<PageData> queryAllApplyDataListForNext(PageData pd);

    // 查询所有工单信息
    List<PageData> queryAllApplyDataList(PageData pd);

    // 查询工单流程审批详情信息
    List<PageData> queryAllApplyDataListForDetails(PageData pd);

    // 查询中台授权用户信息
    List<PageData> querySjztUserList(PageData pd);

    //更新购物车提出申请状态0或1（提交为1，未提交为0）
    void updateshoppingcat(PageData pd);


    //更新购物车（撤回为-2）
    void updateshoppingcatForRevoke(PageData pd);

    PageData querySpjb(PageData pd);

    //查询授权表名
    String querysqbm(@Param("ydxtbm") String ydxtbm,@Param("ydbm") String ydbm,@Param("ydyhm") String ydyhm, @Param("sqrssbm")String sqrssbm);


    /**
     * 移除购物车内容
     * @param pd
     * @return
     */
    int delForShoppingcat(PageData pd);

    /**
     * 根据本部授权表查询地市授权表信息
     * @param pd
     * @return
     */
    List<PageData> querySqTableName(PageData pd);

}












