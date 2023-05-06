package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import utils.DateUtils;
import utils.UUIDUtil;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcProcessAudit;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcProcessAuditDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcProcessAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 审核流程意见表(SjkflcProcessAudit)表服务实现类
 *
 * @author sssJL
 * @since 2021-10-08 15:05:31
 */
@Service("sjkflcProcessAuditService")
public class SjkflcProcessAuditServiceImpl implements SjkflcProcessAuditService {
    @Autowired
    private SjkflcProcessAuditDao sjkflcProcessAuditDao;

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    @Override
    public SjkflcProcessAudit queryById(String demandCode) {
        return sjkflcProcessAuditDao.queryById(demandCode);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcProcessAudit> queryList(SjkflcProcessAudit sjkflcProcessAudit) {
        return sjkflcProcessAuditDao.queryList(sjkflcProcessAudit);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcProcessAudit 实例对象
     * @param pageNum            第几页
     * @param pageSize           每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcProcessAudit> queryListByPage(SjkflcProcessAudit sjkflcProcessAudit, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcProcessAudit> list = sjkflcProcessAuditDao.queryList(sjkflcProcessAudit);
        PageInfo<SjkflcProcessAudit> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SjkflcProcessAudit sjkflcProcessAudit) {
        sjkflcProcessAudit.setId(UUIDUtil.getUUID());
        sjkflcProcessAudit.setCreateTime(DateUtils.getNowTime());
        return sjkflcProcessAuditDao.insert(sjkflcProcessAudit) > 0;
    }

    /**
     * 新增数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 是否成功
     */
    @Override
    public SjkflcProcessAudit insertForModel(SjkflcProcessAudit sjkflcProcessAudit) {
        sjkflcProcessAudit.setId(UUIDUtil.getUUID());
        sjkflcProcessAudit.setCreateTime(DateUtils.getNowTime());
        return sjkflcProcessAuditDao.insertForModel(sjkflcProcessAudit);
    }


    /**
     * 修改数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcProcessAudit sjkflcProcessAudit) {
        return sjkflcProcessAuditDao.update(sjkflcProcessAudit) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String demandCode) {
        return sjkflcProcessAuditDao.deleteById(demandCode) > 0;
    }
}
