package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 全量表查询
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface AllTableDao {

    List<PageData> queryParmList(PageData pd);

    void updateParm(PageData pd);

    boolean insertPerCon(Map<String, Object> map);

    void insertPlan(List<PageData> list);

    int queryCount(String param);

    int queryListCount(PageData pd);

    List querybq(String table_name);
    String queryxxbq(String table_name);
    /**
     * 20210302第二次修改
     */

    // 根据系统查询表信息
    List<PageData> queryTableListBySytem(PageData pd);

    // 根据系统查询表信息总条数
    int queryTableListBySytemCount(PageData pd);

    // 根据系统查询表信息-详情
    List<PageData> queryTableListBySytemDetail(PageData pd);

    // 一级目录=》 根据系统查询表信息-详情
    List<PageData> queryTableListByOneSytemDetail(PageData pd);

    // 查询数据表的总计条数(申请热度) 暂时没用
    int queryTableAllApplyCount(PageData pd);

    // 查询当前表是否已经收藏
    int queryIsCollection(PageData pd);

    // 查询当前表是否已经收藏  贴源层使用
    int queryIsCollectionTyc(PageData pd);

    // 查询共享层表信息
    List<PageData> queryGxcTableList(PageData pd);

    // 查询共享层表信息-字段
    List<PageData> queryGxcTableListColumn(PageData pd);

    // 贴源层是否接入表
    int queryZjztTableCount(PageData pd);
    // 一级目录系统是否接入表
    int queryOneSysZjztTableCount(PageData pd);

    //搜索词存入数据库
    void insertrc(PageData pd);

     //搜索热词
     List<PageData> queryrc(PageData pd);


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
    int queryIsCollectionByGXCXTY(PageData pd);


    /**
     * 根据分析层查询表信息
     */
    List<PageData> queryTableListByFXCXTY(PageData pd);

    /**
     * 查询 分析层的表是否已经收藏
     */
    int queryIsCollectionByFXCXTY(PageData pd);

    /**
     * 查询 查询一级目录是否已经收藏
     */
    int queryIsCollectionForOneSys(PageData pd);
}





















