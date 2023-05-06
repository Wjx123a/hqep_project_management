package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOutDemandApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外部需求受理申请单(SjkflcOutDemandApplication)表数据库访问层
 *
 * @author sssJL
 * @since 2021-10-06 14:13:40
 */
@Repository
public interface SjkflcOutDemandApplicationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcOutDemandApplication queryById(String demandCode);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 对象列表
     */
    List<SjkflcOutDemandApplication> queryList(SjkflcOutDemandApplication sjkflcOutDemandApplication);

    /**
     * 新增数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 影响行数
     */
    int insert(SjkflcOutDemandApplication sjkflcOutDemandApplication);


    /**
     * 新增数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 影响行数
     */
    SjkflcOutDemandApplication insertForModel(SjkflcOutDemandApplication sjkflcOutDemandApplication);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcOutDemandApplication> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcOutDemandApplication> list);

    /**
     * 修改数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 影响行数
     */
    int update(SjkflcOutDemandApplication sjkflcOutDemandApplication);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 影响行数
     */
    int deleteById(String demandCode);

}

