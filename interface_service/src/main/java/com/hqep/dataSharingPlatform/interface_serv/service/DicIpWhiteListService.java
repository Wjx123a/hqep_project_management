package com.hqep.dataSharingPlatform.interface_serv.service;

import com.hqep.dataSharingPlatform.common.model.DicIpWhiteList;
import com.hqep.dataSharingPlatform.common.vo.JsonResult;

import java.util.List;
import java.util.Map;
/**
 * (DicIpWhiteList)表服务接口
 *
 * @author 邵文强
 * @since 2021-03-29 17:00:23
 */
public interface DicIpWhiteListService {

    /**
     * 通过实体查询符合条件数据()
     *
     * @param model  实体类
     * @return 符合条件的实体列表
     */
    List<DicIpWhiteList> selectByModel(DicIpWhiteList model);


    /**
     * 新增一条记录
     *
     * @param dicIpWhiteList 实例对象
     * @return 实例对象
     */
    JsonResult insertOneModel(DicIpWhiteList dicIpWhiteList);

    /**
     * 修改数据
     *
     * @param dicIpWhiteList 实例对象
     * @return 实例对象
     */
    JsonResult updateOneModel(DicIpWhiteList dicIpWhiteList);

    /**
     * 通过主键删除数据
     *
     * @param id  主键ip
     * @return 是否成功
     */
    JsonResult deleteById(String id);

}