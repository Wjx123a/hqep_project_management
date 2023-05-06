package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

/**
 * @类名: LoginDao
 * @功能描述 登陆模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/23
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface LoginDao {
    /*
     * @方法名:loginPersonInfo
     * @功能描述:查询登陆人信息
     * @作者信息：Wang_XD
     * @创建时间:9:47 2019/9/23
     **/
    public PageData loginPersonInfoMapper (PageData pd);
    /**
     * @方法名:updatePasswordMapper
     * @功能描述:修改密码
     * @作者信息：Wang_XD
     * @创建时间:17:02 2019/9/23
     **/
    public Integer updatePasswordMapper (PageData pd);

    /**
     * 重置密码
     * @param pd
     */
    void resetPassword(PageData pd);

    /**
     * 初始化权限
     * @param pd
     */
    void firstMenuAuth(PageData pd);
}
