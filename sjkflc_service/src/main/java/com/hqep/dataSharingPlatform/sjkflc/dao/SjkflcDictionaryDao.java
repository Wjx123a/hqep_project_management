package com.hqep.dataSharingPlatform.sjkflc.dao;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcDictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据字典表(SjkflcDictionary)表数据库访问层
 *
 * @author sssJL
 * @since 2021-10-13 15:34:09
 */
@Repository
public interface SjkflcDictionaryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param dictId 主键
     * @return 实例对象
     */
    SjkflcDictionary queryById(String dictId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcDictionary 实例对象
     * @return 对象列表
     */
    List<SjkflcDictionary> queryList(SjkflcDictionary sjkflcDictionary);

    /**
     * 新增数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 影响行数
     */
    int insert(SjkflcDictionary sjkflcDictionary);


    /**
     * 新增数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 影响行数
     */
    SjkflcDictionary insertForModel(SjkflcDictionary sjkflcDictionary);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SjkflcDictionary> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SjkflcDictionary> list);

    /**
     * 修改数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 影响行数
     */
    int update(SjkflcDictionary sjkflcDictionary);

    /**
     * 通过主键删除数据
     *
     * @param dictId 主键
     * @return 影响行数
     */
    int deleteById(String dictId);

    /**
     * 查询需求承接方部门名称/数据产生部门名称
     * @return
     */
    List<Map> queryDeptList();

    /**
     * 查询需求系统名称/系统编码
     * @return
     */
    List<Map> querySysList();


}

