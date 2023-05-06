package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOutDemandApplication;

import java.util.List;

/**
 * 外部需求受理申请单(SjkflcOutDemandApplication)表服务接口
 *
 * @author sssJL
 * @since 2021-10-06 14:13:39
 */
public interface SjkflcOutDemandApplicationService {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcOutDemandApplication queryById(String demandCode);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcOutDemandApplication> queryList(SjkflcOutDemandApplication sjkflcOutDemandApplication);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @param pageNum                    第几页
     * @param pageSize                   每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcOutDemandApplication> queryListByPage(SjkflcOutDemandApplication sjkflcOutDemandApplication, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcOutDemandApplication sjkflcOutDemandApplication);


    /**
     * 新增数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 影响行数
     */
    SjkflcOutDemandApplication insertForModel(SjkflcOutDemandApplication sjkflcOutDemandApplication);


    /**
     * 修改数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcOutDemandApplication sjkflcOutDemandApplication);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean deleteById(String demandCode);

    /**
     * 通过主键进行撤回操作
     * 更新上报标志数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean backById(String demandCode);

    /**
     * 通过主键进行上报操作
     * 更新上报标志数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean submitById(String demandCode);
}
