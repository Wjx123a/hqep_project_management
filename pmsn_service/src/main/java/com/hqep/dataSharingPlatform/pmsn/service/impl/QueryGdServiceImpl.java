package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.QueryGdDao;
import com.hqep.dataSharingPlatform.pmsn.service.QueryGdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryGdServiceImpl implements QueryGdService {


    @Autowired
    private QueryGdDao dao;


    @Override
    public List<PageData> queryGdCount(PageData pd) {
        return dao.queryGdCount(pd);
    }

    @Override
    public List<PageData> queryrowClickOne(PageData pd) {
        return dao.queryrowClickOne(pd);
    }

    @Override
    public int queryrowClickOneCount(PageData pd) {
        return dao.queryrowClickOneCount(pd);
    }

    @Override
    public int queryGdCountCount(PageData pd) {
        return dao.queryGdCountCount(pd);
    }

    @Override
    public List<PageData> querySpGd(PageData pd) {
        return dao.querySpGd(pd);
    }

    @Override
    public List<PageData> queryProgressToAudit(PageData pd) {
        return dao.queryProgressToAudit(pd);
    }

    @Override
    public void updatePullWorkOrderForRevoke(PageData pd) {
        dao.updatePullWorkOrderForRevoke(pd);
    }

    public void addGd(PageData param) {
        dao.addGd(param);
    }

    public void updateSpZt(PageData param) {
        // updateSpZtByGXCXTY
        dao.updateSpZt(param);
    }

    public void updateSpZtBh(PageData logData) {
        dao.updateSpZtBh(logData);
    }

    public void delShoppingCart(PageData logData) {
        dao.delShoppingCart(logData);
    }
}
