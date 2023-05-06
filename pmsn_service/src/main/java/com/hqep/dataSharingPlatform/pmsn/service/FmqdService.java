package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.apache.poi.ss.usermodel.Sheet;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

public interface FmqdService {

    List<PageData> queryParmSystemName(PageData pd) throws ServiceException;

    List<PageData> queryParmList(PageData pd) throws ServiceException;

    List<PageData> queryParmListForExcel(PageData pd) throws ServiceException;

    List<PageData> queryParmList_column(PageData pd) throws ServiceException;

    void updateParm(PageData pd) throws ServiceException;

    // 暂时没用
    boolean insertPerCon(List<Map<String, Object>> list);

    // 批量保存
    void insertPlan(List<PageData> list) throws ServiceException;

    List<String> errorMsg(Sheet sheet);

    List<PageData> buildList(Sheet sheet) throws ServiceException;

    int queryCount(String param);
}
