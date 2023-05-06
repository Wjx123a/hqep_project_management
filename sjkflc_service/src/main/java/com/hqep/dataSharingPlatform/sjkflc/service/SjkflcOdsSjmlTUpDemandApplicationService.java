package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandApplication;

import java.util.List;

/**
 * 数据上传_需求提报环节信息表(SjkflcOdsSjmlTUpDemandApplication)表服务接口
 *
 * @author sssJL
 * @since 2021-10-09 13:22:57
 */
public interface SjkflcOdsSjmlTUpDemandApplicationService {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcOdsSjmlTUpDemandApplication queryById(String demandCode);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandApplication> queryList(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpDemandApplication> queryListForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @param pageNum                           第几页
     * @param pageSize                          每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcOdsSjmlTUpDemandApplication> queryListByPage(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);


    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 影响行数
     */
    boolean insertForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);


    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 实例对象
     */
    boolean updateForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication);
    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean deleteById(String demandCode);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean deleteByIdForApi(String demandCode);



    List<SjkflcOdsSjmlTUpDemandApplication> noExistence();
}
