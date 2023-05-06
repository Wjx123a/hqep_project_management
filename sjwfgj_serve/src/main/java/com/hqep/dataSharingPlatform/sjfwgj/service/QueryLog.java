package com.hqep.dataSharingPlatform.sjfwgj.service;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.DisposelogDO;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Map;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: querylog
 * @author: wjx
 * @data: 2023/4/25 20:06 PM
 */
public interface QueryLog {
    Map select();

    boolean insert(Map map);
}
