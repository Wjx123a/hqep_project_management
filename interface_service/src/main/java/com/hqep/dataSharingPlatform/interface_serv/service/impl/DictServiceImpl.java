package com.hqep.dataSharingPlatform.interface_serv.service.impl;

import com.hqep.dataSharingPlatform.interface_serv.dao.DictDao;
import com.hqep.dataSharingPlatform.interface_serv.service.DictService;
import com.hqep.dataSharingPlatform.common.utils.JsonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2021/03/20$ 9:42$
 * @Version: 1.0
 */
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao dictDao;

    @Override
    public Map getUserNameList(Map map) {
        return JsonMsg.creatSuccessMapForList(dictDao.getUserNameList(map));
    }

    @Override
    public Map getSystemNameList(Map map) {
        return JsonMsg.creatSuccessMapForList(dictDao.getSystemNameList(map));
    }

    /**
     * 下发订单管理
     * 查询系统名列表/dict/getDownOrderSystemNameList
     *
     * @return
     */
    @Override
    public Map getDownOrderSystemNameList(Map map) {
        return JsonMsg.creatSuccessMapForList(dictDao.getDownOrderSystemNameList(map));
    }

    @Override
    public Map getDeptNameList(Map map) {
        return JsonMsg.creatSuccessMapForList(dictDao.getDeptNameList(map));
    }

    @Override
    public Map getDownOrderDeptNameList(Map map) {
        return JsonMsg.creatSuccessMapForList(dictDao.getDownOrderDeptNameList(map));
    }

    @Override
    public Map getCountOfSjzt(Map map) {
        return  JsonMsg.successObj(dictDao.getCountOfSjzt(map));
    }

    @Override
    public Map getSystemNameListForFmqd(Map map) {
        return JsonMsg.successObj(dictDao.getSystemNameListForFmqd(map));
    }

    @Override
    public Map getUserNameListForFmqd(Map map) {
        return JsonMsg.successObj(dictDao.getUserNameListForFmqd(map));
    }
}
