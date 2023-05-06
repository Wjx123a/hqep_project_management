package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

/**
 * 负面清单查询
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface FmqdDao {
    /**
     * 负面清单页面查询条件系统名称
     * @param pd
     * @return
     */
    List<PageData> queryParmSystemName(PageData pd);

    /**
     * 负面清单页面表格查询
     * @param pd
     * @return
     */
    List<PageData> queryParmList(PageData pd);

    /**
     * 负面清单页面表格查询 导出数据
     * @param pd
     * @return
     * @throws ServiceException
     */
    List<PageData> queryParmListForExcel(PageData pd) throws ServiceException;

    List<PageData> queryParmList_column(PageData pd);

    void updateParm(PageData pd);

    boolean insertPerCon(Map<String, Object> map);

    void insertPlan(List<PageData> list);

    int queryCount(String param);
}