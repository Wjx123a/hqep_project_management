package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;

public interface rcService {
    //查询热词
    List<PageData> queryrc(PageData pd) throws ServiceException;

    // 查询下拉选内容
    List<PageData> querySelectOption(PageData pd) throws ServiceException;
}
