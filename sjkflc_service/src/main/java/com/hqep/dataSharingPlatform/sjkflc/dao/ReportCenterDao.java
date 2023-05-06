package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.CreateTableAndTabbleName;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报表中心 接口相关数据持久层方法
 */
@Repository
public interface ReportCenterDao {
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
    public int grantQueryTableForUser(PageData pd);
    /***
     * 取消授权表
     * @return
     */
    public int revokeQueryTableForUser(PageData pd);

    /***
     * 创建表
     * @return
     */
    public int inertGrantForLogs(PageData pd);


}
