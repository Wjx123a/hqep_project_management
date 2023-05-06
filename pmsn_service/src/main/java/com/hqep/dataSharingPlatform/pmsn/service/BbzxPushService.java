package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

/**
 * 插入报表中心
 */
public interface BbzxPushService {


    public int insertBbzxPushForBBZX(PageData pd);

    public int insertBbzxPushForMe(PageData pd);


}
