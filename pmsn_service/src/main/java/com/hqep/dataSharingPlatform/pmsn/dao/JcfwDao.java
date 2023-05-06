package com.hqep.dataSharingPlatform.pmsn.dao;


import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface JcfwDao {


    List<PageData> queryJcfw(PageData pd);
    List<PageData> queryJcfwmx(PageData pd);

}
