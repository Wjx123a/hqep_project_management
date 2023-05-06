package com.hqep.dataSharingPlatform.interface_serv.dao;

import com.hqep.dataSharingPlatform.common.model.SysLogs;
import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (SysLogs)表数据库访问层
 *
 * @author shaowenqiang
 * @since 2020-11-01 19:27:00
 */
@Repository
public interface SysLogsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysLogs queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysLogs> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    List<SysLogs> queryAll(SysLogs sysLogs);

    /**
     * 查询所有登录信息
     * 最外层的表格数据
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    List<SysLogs> queryAllForLogin(SysLogs sysLogs);

    /**
     * 根据登录用户查询最新的登录记录的详情
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    List<SysLogs> queryAllForLoginToDetail(SysLogs sysLogs);

    /**
     * 根据登录用户查询最新的登录记录
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    List<SysLogs> queryNewLastForPerson(SysLogs sysLogs);

    /**
     * 新增数据
     *
     * @param sysLogs 实例对象
     * @return 影响行数
     */
    int insert(SysLogs sysLogs);

    /**
     * 修改数据
     *
     * @param sysLogs 实例对象
     * @return 影响行数
     */
    int update(SysLogs sysLogs);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    List<String> getIPWhiteList();


    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<String> getPowerRoleNameList();

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<String> getPowerRoleNameListForLoginNum(PageData pd);

    // 根据id查询传入参数json
    String queryParamsById(String id);

    /**
     * 查询 系统日志 操作 下拉选的内容
     * @param sysLogs
     * @return
     */
    List<SysLogs> querySysLogsSelectList(SysLogs sysLogs);

}