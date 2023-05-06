package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.hqep.dataSharingPlatform.common.utils.DataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utils.DateUtils;
import utils.UUIDUtil;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcOdsSjmlTUpAuditProcessDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpAuditProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据上传_审核流程环节信息表(SjkflcOdsSjmlTUpAuditProcess)表服务实现类
 *
 * @author sssJL
 * @since 2021-10-09 13:27:42
 */
@Service("sjkflcOdsSjmlTUpAuditProcessService")
public class SjkflcOdsSjmlTUpAuditProcessServiceImpl implements SjkflcOdsSjmlTUpAuditProcessService {
    @Autowired
    private SjkflcOdsSjmlTUpAuditProcessDao sjkflcOdsSjmlTUpAuditProcessDao;

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    @Override
    public SjkflcOdsSjmlTUpAuditProcess queryById(String demandCode) {
        return sjkflcOdsSjmlTUpAuditProcessDao.queryById(demandCode);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcOdsSjmlTUpAuditProcess> queryList(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        return sjkflcOdsSjmlTUpAuditProcessDao.queryList(sjkflcOdsSjmlTUpAuditProcess);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 对象列表
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public List<SjkflcOdsSjmlTUpAuditProcess> queryListForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        return sjkflcOdsSjmlTUpAuditProcessDao.queryListForApi(sjkflcOdsSjmlTUpAuditProcess);
    }
    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @param pageNum                      第几页
     * @param pageSize                     每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcOdsSjmlTUpAuditProcess> queryListByPage(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcOdsSjmlTUpAuditProcess> list = sjkflcOdsSjmlTUpAuditProcessDao.queryList(sjkflcOdsSjmlTUpAuditProcess);
        PageInfo<SjkflcOdsSjmlTUpAuditProcess> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        sjkflcOdsSjmlTUpAuditProcess.setId(UUIDUtil.getUUID());
        sjkflcOdsSjmlTUpAuditProcess.setBatchTime(DateUtils.getNowDate());
        return sjkflcOdsSjmlTUpAuditProcessDao.insert(sjkflcOdsSjmlTUpAuditProcess) > 0;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 是否成功
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean insertForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        return sjkflcOdsSjmlTUpAuditProcessDao.insertForApi(sjkflcOdsSjmlTUpAuditProcess) > 0;
    }


    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        return sjkflcOdsSjmlTUpAuditProcessDao.update(sjkflcOdsSjmlTUpAuditProcess) > 0;
    }

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 是否成功
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean updateForApi(SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        return sjkflcOdsSjmlTUpAuditProcessDao.updateForApi(sjkflcOdsSjmlTUpAuditProcess) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String demandCode) {
        return sjkflcOdsSjmlTUpAuditProcessDao.deleteById(demandCode) > 0;
    }
    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean deleteByIdForApi(String demandCode) {
        return sjkflcOdsSjmlTUpAuditProcessDao.deleteByIdForApi(demandCode) > 0;
    }


    /**
     *
     * @return 实例对象
     */
    @Override
    public List<SjkflcOdsSjmlTUpAuditProcess> noExistence() {
        return sjkflcOdsSjmlTUpAuditProcessDao.noExistence();
    }

}
