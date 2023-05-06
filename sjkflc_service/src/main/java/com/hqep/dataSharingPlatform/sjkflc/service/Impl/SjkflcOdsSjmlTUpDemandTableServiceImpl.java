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

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandTable;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcOdsSjmlTUpDemandTableDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpDemandTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据上传_需求数据表信息表(SjkflcOdsSjmlTUpDemandTable)表服务实现类
 *
 * @author sssJL
 * @since 2021-10-11 10:13:31
 */
@Service("sjkflcOdsSjmlTUpDemandTableService")
public class SjkflcOdsSjmlTUpDemandTableServiceImpl implements SjkflcOdsSjmlTUpDemandTableService {
    @Autowired
    private SjkflcOdsSjmlTUpDemandTableDao sjkflcOdsSjmlTUpDemandTableDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SjkflcOdsSjmlTUpDemandTable queryById(String id) {
        return sjkflcOdsSjmlTUpDemandTableDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcOdsSjmlTUpDemandTable> queryList(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        return sjkflcOdsSjmlTUpDemandTableDao.queryList(sjkflcOdsSjmlTUpDemandTable);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 对象列表
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public List<SjkflcOdsSjmlTUpDemandTable> queryListForApi(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        return sjkflcOdsSjmlTUpDemandTableDao.queryList(sjkflcOdsSjmlTUpDemandTable);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @param pageNum                     第几页
     * @param pageSize                    每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcOdsSjmlTUpDemandTable> queryListByPage(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcOdsSjmlTUpDemandTable> list = sjkflcOdsSjmlTUpDemandTableDao.queryList(sjkflcOdsSjmlTUpDemandTable);
        PageInfo<SjkflcOdsSjmlTUpDemandTable> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        sjkflcOdsSjmlTUpDemandTable.setId(UUIDUtil.getUUID());
        sjkflcOdsSjmlTUpDemandTable.setModifyType("1");
        return sjkflcOdsSjmlTUpDemandTableDao.insert(sjkflcOdsSjmlTUpDemandTable) > 0;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 是否成功
     */
    // 批量添加保存
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean insertForApi(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        return sjkflcOdsSjmlTUpDemandTableDao.insertForModel(sjkflcOdsSjmlTUpDemandTable) > 0;
    }


    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        return sjkflcOdsSjmlTUpDemandTableDao.update(sjkflcOdsSjmlTUpDemandTable) > 0;
    }

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 是否成功
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean updateForApi(SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        return sjkflcOdsSjmlTUpDemandTableDao.update(sjkflcOdsSjmlTUpDemandTable) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return sjkflcOdsSjmlTUpDemandTableDao.deleteById(id) > 0;
    }
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name=DataSource.DATA_SOURCE_2)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean deleteByIdForApi(String id) {
        return sjkflcOdsSjmlTUpDemandTableDao.deleteById(id) > 0;
    }



    /**
     *
     * @return 实例对象
     */
    @Override
    public List<SjkflcOdsSjmlTUpDemandTable> noExistence() {
        return sjkflcOdsSjmlTUpDemandTableDao.noExistence();
    }
}
