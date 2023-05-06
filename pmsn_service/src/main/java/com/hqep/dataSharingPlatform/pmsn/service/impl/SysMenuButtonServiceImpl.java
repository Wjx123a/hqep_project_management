package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.pmsn.dao.SysMenuButtonDao;
import com.hqep.dataSharingPlatform.pmsn.model.SysMenuButton;
import com.hqep.dataSharingPlatform.pmsn.service.SysMenuButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysMenuButton)表服务实现类
 *
 * @author sssJL
 * @since 2022-11-02 13:44:08
 */
@Service("sysMenuButtonService")
public class SysMenuButtonServiceImpl implements SysMenuButtonService {
    @Autowired
    private SysMenuButtonDao sysMenuButtonDao;

    /**
     * 通过用户ID查询按钮权限
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public int queryById(String id) {
        return sysMenuButtonDao.queryById(id);
    }
    /**
     * 查询多条数据
     *通过用户ID查询按钮权限
     * @return 对象列表
     */
    @Override
    public List<SysMenuButton> queryListAuth(SysMenuButton sysMenuButton) {
        return sysMenuButtonDao.queryListAuth(sysMenuButton);
    }
    /**
     * 查询多条数据
     *
     * @param sysMenuButton 实例对象
     * @return 对象列表
     */
    @Override
    public List<SysMenuButton> queryList(SysMenuButton sysMenuButton) {
        return sysMenuButtonDao.queryList(sysMenuButton);
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sysMenuButton 实例对象
     * @param pageNum       第几页
     * @param pageSize      每页多少条
     * @return PageInfo 对象列表
     */
    @Override
    public PageInfo<SysMenuButton> queryListByPage(SysMenuButton sysMenuButton, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysMenuButton> list = sysMenuButtonDao.queryList(sysMenuButton);
        PageInfo<SysMenuButton> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param sysMenuButton 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(SysMenuButton sysMenuButton) {
        return sysMenuButtonDao.insert(sysMenuButton) > 0;
    }

    /**
     * 新增数据
     *
     * @param sysMenuButton 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insertForModel(SysMenuButton sysMenuButton) {
        return sysMenuButtonDao.insertForModel(sysMenuButton) > 0;
    }


    /**
     * 修改数据
     *
     * @param sysMenuButton 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(SysMenuButton sysMenuButton) {
        return sysMenuButtonDao.update(sysMenuButton) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return sysMenuButtonDao.deleteById(id) > 0;
    }
}
