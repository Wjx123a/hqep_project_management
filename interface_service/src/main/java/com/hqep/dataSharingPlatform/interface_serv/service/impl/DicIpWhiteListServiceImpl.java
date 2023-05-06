package com.hqep.dataSharingPlatform.interface_serv.service.impl;

import com.hqep.dataSharingPlatform.common.model.DicIpWhiteList;
import com.hqep.dataSharingPlatform.common.utils.JsonMsg;
import com.hqep.dataSharingPlatform.common.vo.JsonResult;
import com.hqep.dataSharingPlatform.interface_serv.dao.DicIpWhiteListDao;
import com.hqep.dataSharingPlatform.interface_serv.service.DicIpWhiteListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (DicIpWhiteList)表服务实现类
 *
 * @author 邵文强
 * @since 2021-03-30 08:45:04
 */
@Service("dicIpWhiteListService")
public class DicIpWhiteListServiceImpl implements DicIpWhiteListService {

    @Resource
    private DicIpWhiteListDao dicIpWhiteListDao;

   /**
     * 通过实体查询符合条件数据()
     *
     * @param model  实体类
     * @return 符合条件的实体列表
     */
    @Override
    public List<DicIpWhiteList> selectByModel(DicIpWhiteList model) {
        return this.dicIpWhiteListDao.selectByModel(model);
    }


    /**
     * 新增数据
     *
     * @param dicIpWhiteList 实例对象
     * @return 实例对象
     */
    @Override
    public JsonResult insertOneModel(DicIpWhiteList dicIpWhiteList) {
        int i = this.dicIpWhiteListDao.insert(dicIpWhiteList);
        return JsonResult.autoResultMap(i, dicIpWhiteList,"新增成功","新增失败");
    }

    /**
     * 修改数据
     *
     * @param dicIpWhiteList 实例对象
     * @return 实例对象
     */
    @Override
    public JsonResult updateOneModel(DicIpWhiteList dicIpWhiteList) {
        int i = this.dicIpWhiteListDao.update(dicIpWhiteList);
        return JsonResult.autoResultMap(i, dicIpWhiteList,"更新成功","更新失败");
    }

    /**
     * 通过主键删除数据
     *
     * @param id  主键ip
     * @return 是否成功
     */
    @Override
    public JsonResult deleteById(String id) {
        int i = this.dicIpWhiteListDao.deleteById(id);
        return JsonResult.autoResultMap(i,id,"删除成功","删除失败");
    }
}