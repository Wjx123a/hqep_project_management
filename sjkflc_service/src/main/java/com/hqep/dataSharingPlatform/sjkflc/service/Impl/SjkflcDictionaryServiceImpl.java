package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import utils.DateUtils;
import utils.UUIDUtil;

import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcDictionary;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcDictionaryDao;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据字典表(SjkflcDictionary)表服务实现类
 *
 * @author sssJL
 * @since 2021-10-13 15:34:08
 */
@Service("sjkflcDictionaryService")
public class SjkflcDictionaryServiceImpl implements SjkflcDictionaryService {
    @Autowired
    private SjkflcDictionaryDao sjkflcDictionaryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param dictId 主键
     * @return 实例对象
     */
    @Override
    public SjkflcDictionary queryById(String dictId) {
        return sjkflcDictionaryDao.queryById(dictId);
    }

    /**
     * 查询多条数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 对象列表
     */
    @Override
    public List<SjkflcDictionary> queryList(SjkflcDictionary sjkflcDictionary) {
        return sjkflcDictionaryDao.queryList(sjkflcDictionary);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcDictionary 实例对象
     * @param pageNum          第几页
     * @param pageSize         每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SjkflcDictionary> queryListByPage(SjkflcDictionary sjkflcDictionary, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SjkflcDictionary> list = sjkflcDictionaryDao.queryList(sjkflcDictionary);
        PageInfo<SjkflcDictionary> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SjkflcDictionary sjkflcDictionary) {
        sjkflcDictionary.setDictId(UUIDUtil.getUUID());
        sjkflcDictionary.setCreateTime(DateUtils.getNowDate());
        return sjkflcDictionaryDao.insert(sjkflcDictionary) > 0;
    }

    /**
     * 新增数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 是否成功
     */
    @Override
    public SjkflcDictionary insertForModel(SjkflcDictionary sjkflcDictionary) {
        sjkflcDictionary.setDictId(UUIDUtil.getUUID());
        sjkflcDictionary.setCreateTime(DateUtils.getNowDate());
        return sjkflcDictionaryDao.insertForModel(sjkflcDictionary);
    }


    /**
     * 修改数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SjkflcDictionary sjkflcDictionary) {
        return sjkflcDictionaryDao.update(sjkflcDictionary) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param dictId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String dictId) {
        return sjkflcDictionaryDao.deleteById(dictId) > 0;
    }


    /**
     * 查询需求承接方部门名称/数据产生部门名称
     * @return
     */
    @Override
    public List<Map> queryDeptList() {
        return sjkflcDictionaryDao.queryDeptList();
    }

    /**
     * 查询需求系统名称/系统编码
     * @return
     */
    @Override
    public   List<Map> querySysList(){
        return sjkflcDictionaryDao.querySysList();
    }

}
