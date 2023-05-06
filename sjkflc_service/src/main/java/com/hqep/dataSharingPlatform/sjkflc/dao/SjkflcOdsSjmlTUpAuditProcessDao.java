package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据上传_审核流程环节信息表(SjkflcOdsSjmlTUpAuditProcess)表数据库访问层
 *
 * @author sssJL
 * @since 2021-10-09 13:27:44
 */
@Repository
public interface SjkflcOdsSjmlTUpAuditProcessDao {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcOdsSjmlTUpAuditProcess queryById(String demandCode);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpAuditProcess> queryList(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpAuditProcess> queryListForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 影响行数
     */
    int insert(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);


    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 影响行数
     */
    int insertForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcOdsSjmlTUpAuditProcess> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcOdsSjmlTUpAuditProcess> list);

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 影响行数
     */
    int update(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 影响行数
     */
    int deleteById(String demandCode);
    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 影响行数
     */
    int updateForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

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
    List<SjkflcOdsSjmlTUpAuditProcess> noExistence();
}

