package com.hqep.dataSharingPlatform.sjfwgj.service;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.UserDO;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: test
 * @author: wjx
 * @data: 2023/4/23 11:03 PM
 */
public interface testDao {
    UserDO select();
    UserDO selectByPrimaryKey(Integer id);
}
