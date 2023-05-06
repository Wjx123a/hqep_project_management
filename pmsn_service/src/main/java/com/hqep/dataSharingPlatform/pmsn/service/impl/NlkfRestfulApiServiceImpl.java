package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.NlkfRestfulApiDao;
import com.hqep.dataSharingPlatform.pmsn.service.NlkfRestfulApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @类名: NlkfRestfulApiService
 * @功能描述 能力开放api接口
 * @作者信息 sssJL
 * @创建时间 2021/12/06
 */
@Service
public class NlkfRestfulApiServiceImpl implements NlkfRestfulApiService {

    @Autowired
    NlkfRestfulApiDao dao;

    /**
     * @方法名:queryUserListService
     * @功能描述:查询用户信息
     * @作者信息：sssSL
     * @创建时间 2021/12/06
     **/
    @Override
    public PageData queryUserListService(PageData pd) {
        return dao.queryUserListDao(pd);
    }



    /**
     * @方法名:updateUserInfoService
     * @功能描述:更新用户信息
     * @作者信息：sssSL
     * @创建时间 2021/12/06
     **/
    @Override
    public int updateUserInfoService(PageData pd) {
        return dao.updateUserInfoService(pd);
    }


    /**
     * @方法名:getStatistics
     * @功能描述:查询统计数据
     * @作者信息：sssSL
     * @创建时间 2021/12/06
     **/
    @Override
    public PageData getStatistics(PageData pd) {
        return dao.getStatistics(pd);
    }


    /**
     * @方法名:getSjglyyybList
     * @功能描述:数据管理运营月报数据
     * @作者信息：sssSL
     * @创建时间 2021/12/14
     **/
    @Override
    public List<PageData> getSjglyyybList(PageData pd) {
        return dao.getSjglyyybList(pd);
    }
}
