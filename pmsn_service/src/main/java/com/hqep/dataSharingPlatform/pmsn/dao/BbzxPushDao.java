package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

/**
 * 插入报表中心
 */
@Repository
public interface BbzxPushDao {

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_2)
    public int insertBbzxPushForBBZX(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public int insertBbzxPushForMe(PageData pd);

}
