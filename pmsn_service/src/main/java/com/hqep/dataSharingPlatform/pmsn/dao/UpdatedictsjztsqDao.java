package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import org.springframework.stereotype.Repository;

@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface UpdatedictsjztsqDao {
    void Updatedictsjztsq();
}
