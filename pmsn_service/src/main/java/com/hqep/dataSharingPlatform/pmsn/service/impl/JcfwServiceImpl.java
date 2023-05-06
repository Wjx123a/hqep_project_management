package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.JcfwDao;
import com.hqep.dataSharingPlatform.pmsn.service.JcfwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.List;

@Service
public class JcfwServiceImpl implements JcfwService {

    @Autowired
    private JcfwDao dao;
    @Override
    public List<PageData> queryJcfw(PageData pd) throws ServiceException {
        try {
            return dao.queryJcfw(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<PageData> queryJcfwmx(PageData pd) throws ServiceException {
        try {
            return dao.queryJcfwmx(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
