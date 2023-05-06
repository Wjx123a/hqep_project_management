package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.pmsn.dao.UpdatedictsjztsqDao;
import com.hqep.dataSharingPlatform.pmsn.service.UpdatedictsjztsqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: hqep_project_management
 * @description: 更新授权表dict_sjzt_sq的数据
 * @author:dhr create:2021-10-19 15-46
 **/
@Service
public class UpdatedictsjztsqServiceImpl implements UpdatedictsjztsqService {
    @Autowired
    private UpdatedictsjztsqDao dao;

    @Override
    public void Updatedictsjztsq() {
        dao.Updatedictsjztsq();
    }
}

