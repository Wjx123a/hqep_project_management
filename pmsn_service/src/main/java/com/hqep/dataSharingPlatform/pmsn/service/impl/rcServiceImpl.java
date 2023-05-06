package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.rcDao;
import com.hqep.dataSharingPlatform.pmsn.service.rcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.List;

/**
 * @program: hqep_project_management
 * @description: 热词展示impl
 * @author:dhr create:2021-10-27 14-22
 **/
@Service
public class rcServiceImpl implements rcService {
    @Autowired
    private rcDao dao;

    @Override
    public List<PageData> queryrc(PageData pd) throws ServiceException {
        return dao.queryrc(pd);
    }


    // 查询下拉选内容
    @Override
    public List<PageData> querySelectOption(PageData pd) throws ServiceException {
        return dao.querySelectOption(pd);

    }
}

