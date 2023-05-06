package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;

public interface ProcessManageService {

    // 查询流程信息
    List<PageData> queryProcessList(PageData pd) throws ServiceException;

    // 根据流程查询节点信息
    List<PageData> queryProcessNodeList(PageData pd) throws ServiceException;

    // 修改流程信息
    void updateProcess(PageData pd) throws ServiceException;

    // 修改流程节点信息
    void updateProcessNode(PageData pd) throws ServiceException;

    // 修改流程节点排序
    void updateProcessNodeJdpx(PageData pd) throws ServiceException;

    // 新增流程信息
    void insertProcess(PageData pd) throws ServiceException;

    // 新增流程节点信息
    void insertProcessNode(PageData pd) throws ServiceException;

    // 判断流程编码是否存在
    int queryCount(PageData pd) throws ServiceException;

    // 判断相同流程下的流程节点是否存在
    int queryCountLcjd(PageData pd) throws ServiceException;

    // 判断是否只有一条主流程或者子流程在启用
    int queryProcessStatus(PageData pd) throws ServiceException;

    // 查询当前启用子流程的节点编码
    List<PageData> queryEnableChildProcessNodeList(PageData pd) throws ServiceException;
}
