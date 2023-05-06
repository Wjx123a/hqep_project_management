package com.hqep.dataSharingPlatform.interface_serv.service;

import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/18$ 10:27$
 * @Version: 1.0
 */
public interface PullNewDataService {
    /**
     * 获取用户全量数据
     * 全部更新或插入到mysql中
     * @param result
     * @return
     */
    Map InsertOrUpdateUsers(Map result);

    /**
     * 循环获取字段字典数据，
     * 分批插入或更新到mysql中
     * @param result
     * @return
     */
    Map InsertOrUpdateColumns(Map result);
}
