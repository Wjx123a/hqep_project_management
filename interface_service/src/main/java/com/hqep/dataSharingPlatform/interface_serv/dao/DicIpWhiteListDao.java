package com.hqep.dataSharingPlatform.interface_serv.dao;

import com.hqep.dataSharingPlatform.common.model.DicIpWhiteList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * (DicIpWhiteList)表数据库访问层
 *
 * @author 邵文强
 * @since 2021-03-29 17:00:22
 */
@Repository
public interface DicIpWhiteListDao {

    /**
     * 通过实体查询符合条件数据()
     *
     * @param model  实体类
     * @return 符合条件的实体列表
     */
    List<DicIpWhiteList> selectByModel(DicIpWhiteList model);

     /**
     * 新增数据
     *
     * @param dicIpWhiteList 实例对象
     * @return 影响行数
     */
    int insert(DicIpWhiteList dicIpWhiteList);

    /**
     * 修改数据
     *
     * @param dicIpWhiteList 实例对象
     * @return 影响行数
     */
    int update(DicIpWhiteList dicIpWhiteList);

    /**
     * 通过主键删除数据
     *
     * @param id 主键 ip 
     * @return 影响行数
     */
    int deleteById(String id);

}