package com.hqep.dataSharingPlatform.sjfwgj.service.impl;

import com.hqep.dataSharingPlatform.sjfwgj.dao.UserDOMapper;
import com.hqep.dataSharingPlatform.sjfwgj.pojo.UserDO;
import com.hqep.dataSharingPlatform.sjfwgj.service.testDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @ProjectName: hqep_project_management
 * @ClassName: testimpl
 * @author: wjx
 * @data: 2023/4/23 11:03 PM
 */
@Service
public class Testimpl implements testDao {
    @Autowired
    private UserDOMapper userDOMapper;
    @Override
    public UserDO select() {
        return userDOMapper.selectByPrimaryKey(1);
    }

    @Override
    public UserDO selectByPrimaryKey(Integer id) {
        return userDOMapper.selectByPrimaryKey(id);
    }
}
