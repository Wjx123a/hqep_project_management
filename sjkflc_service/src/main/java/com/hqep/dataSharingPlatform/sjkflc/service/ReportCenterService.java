package com.hqep.dataSharingPlatform.sjkflc.service;


import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.CreateTableAndTabbleName;

import java.util.List;

/**
 * 报表中心 接口相关数据持业务逻辑方法
 */
public interface ReportCenterService {
    /***
     * 创建表
     * @return
     */
    public int createTable(CreateTableAndTabbleName model);

    /***
     * 查询表是否存在
     * @return
     */
    public List<PageData> queryExistsTable(PageData pd);


    /***
     * 授权表
     * @return
     */
    public PageData grantQueryTableForUser(PageData pd);
    /***
     * 取消授权表
     * @return
     */
    public PageData revokeQueryTableForUser(PageData pd);


    /***
     * 创建表
     * @return
     */
    public int inertGrantForLogs(PageData pd);
}
