package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface NlkfRestfulApiDao {

    /**
     * @方法名:queryUserListService
     * @功能描述:查询用户信息
     * @作者信息：sssSL
     * @创建时间 2021/12/06
     **/
    PageData queryUserListDao(PageData pd);

    /**
     * @方法名:updateUserInfoService
     * @功能描述:更新用户信息
     * @作者信息：sssSL
     * @创建时间 2021/12/06
     **/
    int updateUserInfoService(PageData pd);

    /**
     * @方法名:getStatistics
     * @功能描述:查询统计数据
     * @作者信息：sssSL
     * @创建时间 2021/12/06
     **/
    PageData getStatistics(PageData pd);

    /**
     * @方法名:getSjglyyybList
     * @功能描述:数据管理运营月报数据
     * @作者信息：sssSL
     * @创建时间 2021/12/14
     **/
    List<PageData> getSjglyyybList(PageData pd);

}
