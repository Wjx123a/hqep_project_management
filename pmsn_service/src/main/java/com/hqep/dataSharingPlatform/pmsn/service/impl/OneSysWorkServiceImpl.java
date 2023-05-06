package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.OneSysWorkDao;
import com.hqep.dataSharingPlatform.pmsn.service.OneSysWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

@Service
public class OneSysWorkServiceImpl implements OneSysWorkService {

    @Autowired
    OneSysWorkDao dao;

    // 将待申请信息保存至订单栏信息
    @Override
    public int insertDataToShoppingcatOnesys(PageData pd) throws ServiceException {
        return dao.insertDataToShoppingcatOnesys(pd);
    }
    // 通过主键修改数据-订单栏
    @Override
    public int updateDataToShoppingcatOnesys(PageData pd) throws ServiceException {
        return dao.updateDataToShoppingcatOnesys(pd);
    }

    // 通过主键删除数据-订单栏
    @Override
    public int delShoppingcatOnesys(PageData pd) throws ServiceException {
        return dao.delShoppingcatOnesys(pd);
    }

    // 根据登录人ID查询订单栏信息
    @Override
    public List<PageData> queryShoppingcatOnesys(PageData pd) throws ServiceException {
        return dao.queryShoppingcatOnesys(pd);
    }

    // 查询提交订单信息的信息
    @Override
    public List<PageData> queryDataToOnesysWorkOrderList(PageData pd) throws ServiceException {
        return dao.queryDataToOnesysWorkOrderList(pd);
    }

    // 查询提交国网订单子表信息
    @Override
    public List<PageData> queryDataToOnesysWorkTablesList(PageData pd) throws ServiceException {
        return dao.queryDataToOnesysWorkTablesList(pd);
    }

    // 查询提交国网的订单信息的信息（只查询上传国网的相应字段)
    @Override
    public List<Map<String, Object>> queryListOnesysWorkOrderForGwFields(PageData pd) throws ServiceException {
        return dao.queryListOnesysWorkOrderForGwFields(pd);
    }
    // 查询提交国网订单子表信息（只查询上传国网的相应字段)
    @Override
    public List<Map<String, Object>> queryListOnesysWorkTablesForGwFields(PageData pd) throws ServiceException {
        return dao.queryListOnesysWorkTablesForGwFields(pd);
    }

    // 将待申请信息保存至网省创建订单表
    @Override
    public int insertDataToOnesysWorkOrder(PageData pd) throws ServiceException {
        return dao.insertDataToOnesysWorkOrder(pd);
    }

    // 将待申请信息保存至网省创建订单子表
    @Override
    public int insertDataToOnesysWorkTables(Map map) throws ServiceException {
        return dao.insertDataToOnesysWorkTables(map);
    }

    // 将待申请信息保存至网省上传附件表
    @Override
    public int insertDataToOnesysFiles(PageData pd) throws ServiceException {
        return dao.insertDataToOnesysFiles(pd);
    }

    // 通过主键修改数据-网省创建订单表
    @Override
    public int updateDataToOnesysWorkOrder(PageData pd) throws ServiceException {
        return dao.updateDataToOnesysWorkOrder(pd);
    }

    // 通过主键修改数据-网省创建订单子表
    @Override
    public int updateDataToOnesysWorkTables(PageData pd) throws ServiceException {
        return dao.updateDataToOnesysWorkTables(pd);
    }

    // 通过主键修改数据-网省上传附件表
    @Override
    public int updateDataToOnesysWorkFiles(PageData pd) throws ServiceException {
        return dao.updateDataToOnesysWorkFiles(pd);
    }


    // 通过主键删除数据-网省创建订单表
    @Override
    public int deleteByOnesysWorkOrderId(PageData pd) throws ServiceException {
        return dao.deleteByOnesysWorkOrderId(pd);
    }
    // 通过主键删除数据-网省创建订单表
    @Override
    public int deleteByOnesysWorkOrderShoppingcatId(PageData pd) throws ServiceException {
        return dao.deleteByOnesysWorkOrderShoppingcatId(pd);
    }

    // 通过主键删除数据-网省创建订单子表
    @Override
    public int deleteByOnesysWorkTablesId(PageData pd) throws ServiceException {
        return dao.deleteByOnesysWorkTablesId(pd);
    }

    // 通过主键删除数据-网省上传附件表
    @Override
    public int deleteByOnesysWorkFilesId(PageData pd) throws ServiceException {
        return dao.deleteByOnesysWorkFilesId(pd);
    }

    // 查询一级数据下发日志信息
    @Override
    public List<PageData> queryDataToOnesysDownLogsList(PageData pd) throws ServiceException {
        return dao.queryDataToOnesysDownLogsList(pd);
    }

    // 查询一级数据下发附件信息
    @Override
    public List<PageData> queryOnesysDownFilesList(PageData pd) throws ServiceException {
        return dao.queryOnesysDownFilesList(pd);
    }

    // 查询一级系统下发申请工单
    @Override
    public List<PageData> queryOnesysDownOrderList(PageData pd) throws ServiceException {
        return dao.queryOnesysDownOrderList(pd);
    }

    // 查询一级系统下发申请工单子表
    @Override
    public List<PageData> queryOnesysDownTableList(PageData pd) throws ServiceException {
        return dao.queryOnesysDownTableList(pd);
    }

    // 查询一级系统下发审批流程节点
    @Override
    public List<PageData> queryOnesysDownActList(PageData pd) throws ServiceException {
        return dao.queryOnesysDownActList(pd);
    }

    // 新增一级数据下发日志信息
    @Override
    public int insertOnesysDownLogs(PageData pd) {
        return dao.insertOnesysDownLogs(pd);
    }

    // 新增一级数据下发附件信息
    @Override
    public int insertOnesysDownFiles(PageData pd) throws ServiceException {
        return dao.insertOnesysDownFiles(pd);
    }

    // 新增一级系统下发申请工单
    @Override
    public int insertOnesysDownOrder(PageData pd) throws ServiceException {
        return dao.insertOnesysDownOrder(pd);
    }

    // 新增一级系统下发申请工单子表
    @Override
    public int insertOnesysDownTable(Map map) throws ServiceException {
        return dao.insertOnesysDownTable(map);
    }

    // 新增一级系统下发审批流程节点
    @Override
    public int insertOnesysDownAct(PageData pd) throws ServiceException {
        return dao.insertOnesysDownAct(pd);
    }


}
