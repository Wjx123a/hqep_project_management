package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: hqep_project_management
 * @ClassName revokeAuthJobDao
 * @author: sssJL
 * @create: 2023-03-22 13:40
 * @Version V1.0
 * @description:
 **/
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface RevokeAuthJobDao {

     /**
      * 定时设置使用时间收回权限
      * @return
      */
     int revokeAuthJob();


     List<PageData> queryRevokeAuthList(PageData pd);
}
