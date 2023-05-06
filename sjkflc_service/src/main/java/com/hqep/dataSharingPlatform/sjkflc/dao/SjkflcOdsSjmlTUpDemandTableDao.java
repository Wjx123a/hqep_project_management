package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据上传_需求数据表信息表(SjkflcOdsSjmlTUpDemandTable)表数据库访问层
 *
 * @author sssJL
 * @since 2021-10-11 10:13:32
 */
@Repository
public interface SjkflcOdsSjmlTUpDemandTableDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SjkflcOdsSjmlTUpDemandTable queryById(String id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandTable> queryList(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 影响行数
     */
    int insert(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);


    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 影响行数
     */
    int insertForModel(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcOdsSjmlTUpDemandTable> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcOdsSjmlTUpDemandTable> list);

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 影响行数
     */
    int update(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandTable> noExistence();

}

