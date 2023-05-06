package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcSecretRelated;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 涉密数据判定表(SjkflcSecretRelated)表数据库访问层
 *
 * @author sssJL
 * @since 2021-10-27 15:06:55
 */
@Repository
public interface SjkflcSecretRelatedDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SjkflcSecretRelated queryById(String id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 对象列表
     */
    List<SjkflcSecretRelated> queryList(SjkflcSecretRelated sjkflcSecretRelated);

    /**
     * 新增数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 影响行数
     */
    int insert(SjkflcSecretRelated sjkflcSecretRelated);


    /**
     * 新增数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 影响行数
     */
    SjkflcSecretRelated insertForModel(SjkflcSecretRelated sjkflcSecretRelated);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcSecretRelated> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcSecretRelated> list);

    /**
     * 修改数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 影响行数
     */
    int update(SjkflcSecretRelated sjkflcSecretRelated);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

