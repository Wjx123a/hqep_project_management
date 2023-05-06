package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcSecretRelated;

import java.util.List;

/**
 * 涉密数据判定表(SjkflcSecretRelated)表服务接口
 *
 * @author sssJL
 * @since 2021-10-27 15:06:54
 */
public interface SjkflcSecretRelatedService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SjkflcSecretRelated queryById(String id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcSecretRelated> queryList(SjkflcSecretRelated sjkflcSecretRelated);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcSecretRelated 实例对象
     * @param pageNum             第几页
     * @param pageSize            每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcSecretRelated> queryListByPage(SjkflcSecretRelated sjkflcSecretRelated, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcSecretRelated sjkflcSecretRelated);


    /**
     * 新增数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 影响行数
     */
    SjkflcSecretRelated insertForModel(SjkflcSecretRelated sjkflcSecretRelated);


    /**
     * 修改数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcSecretRelated sjkflcSecretRelated);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
