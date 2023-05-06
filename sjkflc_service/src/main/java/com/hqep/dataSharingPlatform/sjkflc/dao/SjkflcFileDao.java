package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据开放流程-文件管理(SjkflcFile)表数据库访问层
 *
 * @author liuzg
 * @since 2021-10-17 12:18:24
 */
@Repository
public interface SjkflcFileDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SjkflcFile queryById(String id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcFile 实例对象
     * @return 对象列表
     */
    List<SjkflcFile> queryList(SjkflcFile sjkflcFile);

    /**
     * 新增数据
     *
     * @param sjkflcFile 实例对象
     * @return 影响行数
     */
    int insert(SjkflcFile sjkflcFile);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcFile> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcFile> list);

    /**
     * 修改数据
     *
     * @param sjkflcFile 实例对象
     * @return 影响行数
     */
    int update(SjkflcFile sjkflcFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

