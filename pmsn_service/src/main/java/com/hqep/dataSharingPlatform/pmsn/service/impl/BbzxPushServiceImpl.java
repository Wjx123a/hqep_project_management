package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.BbzxPushDao;
import com.hqep.dataSharingPlatform.pmsn.service.BbzxPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BbzxPushServiceImpl implements BbzxPushService {


    @Autowired
    BbzxPushDao bbzxPushDao;
    @Override
    public int insertBbzxPushForBBZX(PageData pd) {
        return bbzxPushDao.insertBbzxPushForBBZX(pd);
    }

    @Override
    public int insertBbzxPushForMe(PageData pd) {
        return bbzxPushDao.insertBbzxPushForMe(pd);
    }
}
