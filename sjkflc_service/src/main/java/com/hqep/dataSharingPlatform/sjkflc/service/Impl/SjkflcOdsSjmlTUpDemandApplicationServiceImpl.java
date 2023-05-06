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

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcOdsSjmlTUpDemandApplicationDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpDemandApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据上传_需求提报环节信息表(SjkflcOdsSjmlTUpDemandApplication)表服务实现类
 *
 * @author sssJL
 * @since 2021-10-09 13:22:59
 */
@Service("sjkflcOdsSjmlTUpDemandApplicationService")
public class SjkflcOdsSjmlTUpDemandApplicationServiceImpl implements SjkflcOdsSjmlTUpDemandApplicationService {
    @Autowired
    private SjkflcOdsSjmlTUpDemandApplicationDao sjkflcOdsSjmlTUpDemandApplicationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param demandCode 主键
     * @return 实例对象
     */
    @Override
    public SjkflcOdsSjmlTUpDemandApplication queryById(String demandCode) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.queryById(demandCode);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcOdsSjmlTUpDemandApplication> queryList(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.queryList(sjkflcOdsSjmlTUpDemandApplication);
    }

    /**
     * 查询多条数据
     * 调接口查询第二数据库源
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcOdsSjmlTUpDemandApplication> queryListForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.queryListForApi(sjkflcOdsSjmlTUpDemandApplication);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @param pageNum                           第几页
     * @param pageSize                          每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcOdsSjmlTUpDemandApplication> queryListByPage(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcOdsSjmlTUpDemandApplication> list = sjkflcOdsSjmlTUpDemandApplicationDao.queryList(sjkflcOdsSjmlTUpDemandApplication);
        PageInfo<SjkflcOdsSjmlTUpDemandApplication> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 是否成功
     */
    // 批量添加保存
    @DataSource(name=DataSource.DATA_SOURCE_1)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean insert(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.insert(sjkflcOdsSjmlTUpDemandApplication) > 0;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 是否成功
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean insertForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.insertForApi(sjkflcOdsSjmlTUpDemandApplication) > 0;
    }


    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.update(sjkflcOdsSjmlTUpDemandApplication) > 0;
    }

    /**
     * 修改数据
     * 调接口修改第二数据库源
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 是否成功
     */
    @Override
    public boolean updateForApi(SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.updateForApi(sjkflcOdsSjmlTUpDemandApplication) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param demandCode 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String demandCode) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.deleteById(demandCode) > 0;
    }

    /**
     * 通过主键删除数据
     * 调接口删除第二数据库源
     * @param demandCode 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteByIdForApi(String demandCode) {
        return sjkflcOdsSjmlTUpDemandApplicationDao.deleteByIdForApi(demandCode) > 0;
    }


    /**
     *
     * @return 实例对象
     */
    @Override
    public List<SjkflcOdsSjmlTUpDemandApplication> noExistence() {
        return sjkflcOdsSjmlTUpDemandApplicationDao.noExistence();
    }
}
