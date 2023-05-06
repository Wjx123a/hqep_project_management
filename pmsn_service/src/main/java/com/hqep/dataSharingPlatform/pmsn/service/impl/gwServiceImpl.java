package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.gwDao;
import com.hqep.dataSharingPlatform.pmsn.model.Gwthree;
import com.hqep.dataSharingPlatform.pmsn.service.gwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

@Service
public class gwServiceImpl implements gwService {
    @Autowired
    private gwDao dao;

    @Override
    public boolean insert(Map map1) throws ServiceException {
        return dao.insert(map1);
    }

    @Override
    public boolean insertfile(Map map1) throws ServiceException {
        return dao.insertfile(map1);
    }

    @Override
    public Map selectdate() throws ServiceException {
        return dao.selectdate();
    }

    @Override
    public Map selectfile() throws ServiceException {
        return dao.selectfile();
    }
}
