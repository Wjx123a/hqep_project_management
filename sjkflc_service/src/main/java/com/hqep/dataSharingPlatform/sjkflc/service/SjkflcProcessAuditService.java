package com.hqep.dataSharingPlatform.sjkflc.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcProcessAudit;

import java.util.List;

/**
 * 审核流程意见表(SjkflcProcessAudit)表服务接口
 *
 * @author sssJL
 * @since 2021-10-08 15:05:31
 */
public interface SjkflcProcessAuditService {

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    SjkflcProcessAudit queryById(String demandCode);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SjkflcProcessAudit> queryList(SjkflcProcessAudit sjkflcProcessAudit);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcProcessAudit 实例对象
     * @param pageNum            第几页
     * @param pageSize           每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SjkflcProcessAudit> queryListByPage(SjkflcProcessAudit sjkflcProcessAudit, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 实例对象
     */
    boolean insert(SjkflcProcessAudit sjkflcProcessAudit);


    /**
     * 新增数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 影响行数
     */
    SjkflcProcessAudit insertForModel(SjkflcProcessAudit sjkflcProcessAudit);


    /**
     * 修改数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 实例对象
     */
    boolean update(SjkflcProcessAudit sjkflcProcessAudit);

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    boolean deleteById(String demandCode);

}
