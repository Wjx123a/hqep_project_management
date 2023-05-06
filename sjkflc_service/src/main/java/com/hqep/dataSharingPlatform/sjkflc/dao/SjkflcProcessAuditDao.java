package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcProcessAudit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 审核流程意见表(SjkflcProcessAudit)表数据库访问层
 *
 * @author sssJL
 * @since 2021-10-08 15:05:32
 */
@Repository
public interface SjkflcProcessAuditDao {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcProcessAudit queryById(String demandCode);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 对象列表
     */
    List<SjkflcProcessAudit> queryList(SjkflcProcessAudit sjkflcProcessAudit);

    /**
     * 新增数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 影响行数
     */
    int insert(SjkflcProcessAudit sjkflcProcessAudit);


    /**
     * 新增数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 影响行数
     */
    SjkflcProcessAudit insertForModel(SjkflcProcessAudit sjkflcProcessAudit);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcProcessAudit> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcProcessAudit> list);

    /**
     * 修改数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 影响行数
     */
    int update(SjkflcProcessAudit sjkflcProcessAudit);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 影响行数
     */
    int deleteById(String demandCode);

}

