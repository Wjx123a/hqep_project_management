package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import java.util.List;

/**
 * @program: hqep_project_management
 * @ClassName RevokeAuthJobService
 * @author: sssJL
 * @create: 2023-03-22 13:50
 * @Version V1.0
 * @description:
 **/
public interface RevokeAuthJobService {

    /**
     * 定时设置使用时间收回权限
     * @return
     */
    int revokeAuthJob();


    List<PageData> queryRevokeAuthList(PageData pd);
}
