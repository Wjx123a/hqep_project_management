package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcDictionary;

import java.util.List;
import java.util.Map;

/**
 * 数据字典表(SjkflcDictionary)表服务接口
 *
 * @author sssJL
 * @since 2021-10-13 15:34:08
 */
public interface SjkflcDictionaryService {

    /**
     * 通过ID查询单条数据
     *
     * @param dictId 主键
     * @return 实例对象
     */
    SjkflcDictionary queryById(String dictId);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcDictionary> queryList(SjkflcDictionary sjkflcDictionary);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcDictionary 实例对象
     * @param pageNum          第几页
     * @param pageSize         每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcDictionary> queryListByPage(SjkflcDictionary sjkflcDictionary, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcDictionary sjkflcDictionary);


    /**
     * 新增数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 影响行数
     */
    SjkflcDictionary insertForModel(SjkflcDictionary sjkflcDictionary);


    /**
     * 修改数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcDictionary sjkflcDictionary);

    /**
     * 通过主键删除数据
     *
     * @param dictId 主键
     * @return 是否成功
     */
    boolean deleteById(String dictId);

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
