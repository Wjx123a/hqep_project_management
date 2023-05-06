package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流程管理、节点管理
 */
@Repository
public interface SjkflcProcessManageDao {

    // 查询流程信息
    List<PageData> queryProcessList(PageData pd);

    // 根据流程查询节点信息
    List<PageData> queryProcessNodeList(PageData pd);

    // 修改流程信息
    void updateProcess(PageData pd);

    // 修改流程节点信息
    void updateProcessNode(PageData pd);

    // 修改流程节点排序
    void updateProcessNodeJdpx(PageData pd);

    // 新增流程信息
    void insertProcess(PageData pd);

    // 新增流程节点信息
    void insertProcessNode(PageData pd);

    // 判断流程编码是否存在
    int queryCount(PageData pd);

    // 判断相同流程下的流程节点是否存在
    int queryCountLcjd(PageData pd);

    // 判断是否只有一条主流程或者子流程在启用
    int queryProcessStatus(PageData pd);

    // 查询当前启用子流程的节点编码
    List<PageData> queryEnableChildProcessNodeList(PageData pd);

}