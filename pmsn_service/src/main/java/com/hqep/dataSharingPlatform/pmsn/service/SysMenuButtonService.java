package com.hqep.dataSharingPlatform.pmsn.service;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.pmsn.model.SysMenuButton;

import java.util.List;

/**
 * (SysMenuButton)表服务接口
 *
 * @author sssJL
 * @since 2022-11-02 13:44:08
 */
public interface SysMenuButtonService {

    /**
     * 通过用户ID查询按钮权限
     *
     * @param id 主键
     * @return 实例对象
     */
    int queryById(String id);

    /**
     * 查询多条数据
     *通过用户ID查询按钮权限
     * @return 对象列表
     */
    List<SysMenuButton> queryListAuth(SysMenuButton sysMenuButton);
    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<SysMenuButton> queryList(SysMenuButton sysMenuButton);

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sysMenuButton 实例对象
     * @param pageNum       第几页
     * @param pageSize      每页多少条
     * @return PageInfo 对象列表
     */
    PageInfo<SysMenuButton> queryListByPage(SysMenuButton sysMenuButton, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sysMenuButton 实例对象
     * @return 实例对象
     */
    boolean insert(SysMenuButton sysMenuButton);


    /**
     * 新增数据
     *
     * @param sysMenuButton 实例对象
     * @return 影响行数
     */
    boolean insertForModel(SysMenuButton sysMenuButton);


    /**
     * 修改数据
     *
     * @param sysMenuButton 实例对象
     * @return 实例对象
     */
    boolean update(SysMenuButton sysMenuButton);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
