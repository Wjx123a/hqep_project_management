package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandTable;

import java.util.List;

/**
 * 数据上传_需求数据表信息表(SjkflcOdsSjmlTUpDemandTable)表服务接口
 *
 * @author sssJL
 * @since 2021-10-11 10:13:30
 */
public interface SjkflcOdsSjmlTUpDemandTableService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SjkflcOdsSjmlTUpDemandTable queryById(String id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandTable> queryList(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandTable> queryListForApi(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @param pageNum                     第几页
     * @param pageSize                    每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcOdsSjmlTUpDemandTable> queryListByPage(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);


    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 影响行数
     */
    boolean insertForApi(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);


    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 实例对象
     */
    boolean updateForApi(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteByIdForApi(String id);


    List<SjkflcOdsSjmlTUpDemandTable> noExistence();

}
