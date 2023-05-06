package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据上传_需求提报环节信息表(SjkflcOdsSjmlTUpDemandApplication)表数据库访问层
 *
 * @author sssJL
 * @since 2021-10-09 13:23:00
 */
@Repository
public interface SjkflcOdsSjmlTUpDemandApplicationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcOdsSjmlTUpDemandApplication queryById(String demandCode);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandApplication> queryList(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandApplication> queryListForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 影响行数
     */
    int insert(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);


    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 影响行数
     */
    int insertForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcOdsSjmlTUpDemandApplication> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcOdsSjmlTUpDemandApplication> list);

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 影响行数
     */
    int update(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 影响行数
     */
    int updateForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 影响行数
     */
    int deleteById(String demandCode);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 影响行数
     */
    int deleteByIdForApi(String demandCode);


    /**
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandApplication> noExistence();
}

