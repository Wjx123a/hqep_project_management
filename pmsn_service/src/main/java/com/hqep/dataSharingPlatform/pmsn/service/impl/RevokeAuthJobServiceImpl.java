package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.RevokeAuthJobDao;
import com.hqep.dataSharingPlatform.pmsn.service.RevokeAuthJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: hqep_project_management
 * @ClassName RevokeAuthJobServiceImpl
 * @author: sssJL
 * @create: 2023-03-22 13:51
 * @Version V1.0
 * @description:
 **/
@Service
public class RevokeAuthJobServiceImpl implements RevokeAuthJobService {


    @Autowired
    RevokeAuthJobDao dao;

    /**
     * 定时设置使用时间收回权限
     * @return
     */
    @Override
    public int revokeAuthJob() {
        return dao.revokeAuthJob();
    }

    @Override
    public List<PageData> queryRevokeAuthList(PageData pd) {
        return dao.queryRevokeAuthList(pd);
    }
}
