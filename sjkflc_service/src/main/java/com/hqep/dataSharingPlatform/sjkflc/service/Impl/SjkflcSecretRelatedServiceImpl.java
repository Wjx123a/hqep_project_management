package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import utils.DateUtils;
import utils.UUIDUtil;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcSecretRelated;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcSecretRelatedDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcSecretRelatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 涉密数据判定表(SjkflcSecretRelated)表服务实现类
 *
 * @author sssJL
 * @since 2021-10-27 15:06:54
 */
@Service("sjkflcSecretRelatedService")
public class SjkflcSecretRelatedServiceImpl implements SjkflcSecretRelatedService {
    @Autowired
    private SjkflcSecretRelatedDao sjkflcSecretRelatedDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SjkflcSecretRelated queryById(String id) {
        return sjkflcSecretRelatedDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcSecretRelated> queryList(SjkflcSecretRelated sjkflcSecretRelated) {
        return sjkflcSecretRelatedDao.queryList(sjkflcSecretRelated);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcSecretRelated 实例对象
     * @param pageNum             第几页
     * @param pageSize            每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcSecretRelated> queryListByPage(SjkflcSecretRelated sjkflcSecretRelated, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcSecretRelated> list = sjkflcSecretRelatedDao.queryList(sjkflcSecretRelated);
        PageInfo<SjkflcSecretRelated> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SjkflcSecretRelated sjkflcSecretRelated) {
        sjkflcSecretRelated.setId(UUIDUtil.getUUID());
        return sjkflcSecretRelatedDao.insert(sjkflcSecretRelated) > 0;
    }

    /**
     * 新增数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 是否成功
     */
    @Override
    public SjkflcSecretRelated insertForModel(SjkflcSecretRelated sjkflcSecretRelated) {
        sjkflcSecretRelated.setId(UUIDUtil.getUUID());
        return sjkflcSecretRelatedDao.insertForModel(sjkflcSecretRelated);
    }


    /**
     * 修改数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcSecretRelated sjkflcSecretRelated) {
        return sjkflcSecretRelatedDao.update(sjkflcSecretRelated) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return sjkflcSecretRelatedDao.deleteById(id) > 0;
    }
}
