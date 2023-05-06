package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.pmsn.model.SysMenuButton;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (SysMenuButton)表数据库访问层
 *
 * @author sssJL
 * @since 2022-11-02 13:44:09
 */
@Repository
public interface SysMenuButtonDao {

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
     * 通过实体作为筛选条件查询
     *
     * @param sysMenuButton 实例对象
     * @return 对象列表
     */
    List<SysMenuButton> queryList(SysMenuButton sysMenuButton);

    /**
     * 新增数据
     *
     * @param sysMenuButton 实例对象
     * @return 影响行数
     */
    int insert(SysMenuButton sysMenuButton);


    /**
     * 新增数据
     *
     * @param sysMenuButton 实例对象
     * @return 影响行数
     */
    int insertForModel(SysMenuButton sysMenuButton);


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<SysMenuButton> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SysMenuButton> list);

    /**
     * 修改数据
     *
     * @param sysMenuButton 实例对象
     * @return 影响行数
     */
    int update(SysMenuButton sysMenuButton);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

