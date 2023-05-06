package com.hqep.dataSharingPlatform.sjfwgj.service.impl;

import com.hqep.dataSharingPlatform.sjfwgj.dao.LogDOMapper;
import com.hqep.dataSharingPlatform.sjfwgj.service.QueryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: queryLogimpl
 * @author: wjx
 * @data: 2023/4/25 20:09 PM
 */
@Service
public class queryLogimpl implements QueryLog {
    @Autowired
    private LogDOMapper logDOMapper;

    @Override
    public Map select() {
        return logDOMapper.select();
    }

    @Override
    public boolean insert(Map map) {
        return logDOMapper.insert(map);
    }
}
