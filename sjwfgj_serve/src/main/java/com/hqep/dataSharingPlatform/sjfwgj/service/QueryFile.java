package com.hqep.dataSharingPlatform.sjfwgj.service;

import java.util.Map;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: QueryFile
 * @author: wjx
 * @data: 2023/5/4 19:33 PM
 */
public interface QueryFile {
    boolean insertfile(Map map1);

    Map select();
}
