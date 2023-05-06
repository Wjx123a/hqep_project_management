package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;

import java.util.List;

/**
 * 数据上传_审核流程环节信息表(SjkflcOdsSjmlTUpAuditProcess)表服务接口
 *
 * @author sssJL
 * @since 2021-10-09 13:27:41
 */
public interface SjkflcOdsSjmlTUpAuditProcessService {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcOdsSjmlTUpAuditProcess queryById(String demandCode);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpAuditProcess> queryList(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcOdsSjmlTUpAuditProcess> queryListForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @param pageNum                      第几页
     * @param pageSize                     每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcOdsSjmlTUpAuditProcess> queryListByPage(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);


    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 影响行数
     */
    boolean insertForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);


    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean deleteById(String demandCode);


    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 实例对象
     */
    boolean updateForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean deleteByIdForApi(String demandCode);


    List<SjkflcOdsSjmlTUpAuditProcess> noExistence();
}
