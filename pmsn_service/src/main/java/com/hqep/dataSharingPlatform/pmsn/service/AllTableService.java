package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.apache.poi.ss.usermodel.Sheet;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

public interface AllTableService {

    /**
     * 负面清单页面查询条件系统名称
     * @param pd
     * @return
     * @throws ServiceException
     */

    List<PageData> queryParmList(PageData pd) throws ServiceException;

    void updateParm(PageData pd) throws ServiceException;

    // 暂时没用
    boolean insertPerCon(List<Map<String, Object>> list);

    // 批量保存
    void insertPlan(List<PageData> list) throws ServiceException;

    List<String> errorMsg(Sheet sheet);

    List<PageData> buildList(Sheet sheet) throws ServiceException;

    int queryCount(String param);

    int queryListCount(PageData pd) throws ServiceException;

    /**
     * 20210302第二次修改
     */
    //查询数据标签
    List querbq(String table_name) throws ServiceException;
    //查询数据标签
    String querxxbq(String table_name) throws ServiceException;

    // 根据系统查询表信息
    List<PageData> queryTableListBySytem(PageData pd) throws ServiceException;

    // 根据系统查询表信息总条数
    int queryTableListBySytemCount(PageData pd) throws ServiceException;

    // 根据系统查询表信息-详情
    List<PageData> queryTableListBySytemDetail(PageData pd) throws ServiceException;

    // 一级目录=》 根据系统查询表信息-详情
    List<PageData> queryTableListByOneSytemDetail(PageData pd) throws ServiceException;

    // 查询数据表的总计条数(申请热度) 暂时没用
    int queryTableAllApplyCount(PageData pd) throws ServiceException;

    // 查询当前表是否已经收藏
    int queryIsCollection(PageData pd) throws ServiceException;

    // 查询当前表是否已经收藏  贴源层使用
    int queryIsCollectionTyc(PageData pd) throws ServiceException;

    // 查询共享层表信息
    List<PageData> queryGxcTableList(PageData pd) throws ServiceException;

    // 查询共享层表信息-字段
    List<PageData> queryGxcTableListColumn(PageData pd) throws ServiceException;

    // 贴源层是否接入表
    int queryZjztTableCount(PageData pd) throws ServiceException;

    // 一级目录系统是否接入表
    int queryOneSysZjztTableCount(PageData pd) throws ServiceException;
    //搜索词存入数据库
    void insertrc(PageData pd) throws ServiceException;

    //搜索词存入数据库
    List<PageData> queryrc(PageData pd) throws ServiceException;

    /**
     * 根据贴源层查询表信息
     */
    List<PageData> queryTableListByTYCSystem(PageData pd);

    /**
     * 根据共享层查询表信息
     */
    List<PageData> queryTableListByGXCXTY(PageData pd);

    /**
     * 根据共享层查询表的详情信息
     */
    List<PageData> queryTableListByGXCXTYForDetails(PageData pd);

    /**
     * 根据分析层查询表的详情信息
     */
    List<PageData> queryTableListByFXCXTYForDetails(PageData pd);

    /**
     * 根据贴源层查询表的详情信息
     */
    List<PageData> queryTableListByTYCXTYForDetails(PageData pd);


    /**
     * 根据共享层查询表信息求和
     */
    int queryTableListByGXCXTYCount(PageData pd);

    /**
     * 查询 共享层的表是否已经收藏
     */
    int queryIsCollectionByGXCXTY(PageData pd) throws ServiceException;

    /**
     * 根据分析层查询表信息
     */
    List<PageData> queryTableListByFXCXTY(PageData pd);

    /**
     * 查询 共享层的表是否已经收藏
     */
    int queryIsCollectionByFXCXTY(PageData pd) throws ServiceException;


    /**
     * 查询 查询一级目录是否已经收藏
     */
    int queryIsCollectionForOneSys(PageData pd) throws ServiceException;
}



















