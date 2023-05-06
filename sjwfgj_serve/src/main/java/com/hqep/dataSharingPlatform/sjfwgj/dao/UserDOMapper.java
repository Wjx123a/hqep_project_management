package com.hqep.dataSharingPlatform.sjfwgj.dao;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.UserDO;
import org.springframework.stereotype.Repository;
@Repository
public interface UserDOMapper {

    int deleteByPrimaryKey(Integer id);

      int insert(UserDO record);


    int insertSelective(UserDO record);


    UserDO selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(UserDO record);


    int updateByPrimaryKey(UserDO record);
}