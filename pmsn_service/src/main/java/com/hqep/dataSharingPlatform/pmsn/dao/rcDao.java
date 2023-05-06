package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import javax.xml.rpc.ServiceException;
import java.util.List;

@Repository
public interface rcDao {

    //查询热词
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryrc(PageData pd);

    // 查询下拉选内容
    List<PageData> querySelectOption(PageData pd) ;
}
