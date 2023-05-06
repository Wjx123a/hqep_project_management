package com.hqep.dataSharingPlatform.interface_serv.service.impl;

import com.hqep.dataSharingPlatform.interface_serv.service.SysLogsService;
import com.hqep.dataSharingPlatform.interface_serv.dao.SysLogsDao;
import com.hqep.dataSharingPlatform.common.model.SysLogs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (SysLogs)表服务实现类
 *
 * @author shaowenqiang
 * @since 2020-11-01 19:27:30
 */
@Service("sysLogsService")
public class SysLogsServiceImpl implements SysLogsService {
    @Resource
    private SysLogsDao sysLogsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysLogs queryById(Long id) {
        return this.sysLogsDao.queryById(id);
    }

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysLogs> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit) {
        return this.sysLogsDao.queryAllByLimit(offset, limit);
    }


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    @Override
    public List<SysLogs> queryAll(SysLogs sysLogs) {

        return this.sysLogsDao.queryAll(sysLogs);
    }

    /**
     * 查询所有登录信息
     * 最外层的表格数据
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    @Override
    public List<SysLogs> queryAllForLogin(SysLogs sysLogs) {
        return this.sysLogsDao.queryAllForLogin(sysLogs);
    }


    /**
     * 根据登录用户查询最新的登录记录的详情
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    @Override
    public List<SysLogs> queryAllForLoginToDetail(SysLogs sysLogs) {
        return this.sysLogsDao.queryAllForLoginToDetail(sysLogs);
    }


    /**
     * 根据登录用户查询最新的登录记录
     * @param sysLogs 实例对象
     * @return 对象列表
     */
    @Override
    public List<SysLogs> queryNewLastForPerson(SysLogs sysLogs) {
        return this.sysLogsDao.queryNewLastForPerson(sysLogs);
    }

    /**
     * 新增数据
     *
     * @param sysLogs 实例对象
     * @return 影响行数
     */
    @Override
    public int insert(SysLogs sysLogs) {
        sysLogs.setCreatedTime(new Date());
        return this.sysLogsDao.insert(sysLogs);
    }

    /**
     * 修改数据
     *
     * @param sysLogs 实例对象
     * @return 影响行数
     */
    @Override
    public int update(SysLogs sysLogs) {
        return this.sysLogsDao.update(sysLogs);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Override
    public int deleteById(Long id) {
        return this.sysLogsDao.deleteById(id);
    }

    // 根据id查询传入参数json
    @Override
    public String queryParamsById(String id){
        return sysLogsDao.queryParamsById(id);
    }



    /**
     * 查询 系统日志 操作 下拉选的内容
     * @param sysLogs
     * @return
     */
    @Override
    public List<SysLogs> querySysLogsSelectList(SysLogs sysLogs) {
        return sysLogsDao.querySysLogsSelectList(sysLogs);
    }
}