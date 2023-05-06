package com.hqep.dataSharingPlatform.sjfwgj.service.impl;

import com.hqep.dataSharingPlatform.sjfwgj.dao.FileDOMapper;
import com.hqep.dataSharingPlatform.sjfwgj.service.QueryFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: QueryFileimpl
 * @author: wjx
 * @data: 2023/5/4 19:34 PM
 */
@Service
public class QueryFileimpl implements QueryFile {
    @Autowired
    private FileDOMapper fileDOMapper;
    @Override
    public boolean insertfile(Map map1) {
        return fileDOMapper.insert(map1);
    }

    @Override
    public Map select() {
        return fileDOMapper.select();
    }
}
