package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import javax.xml.rpc.ServiceException;
import java.util.List;

public interface JcfwService {
    List<PageData> queryJcfw(PageData pd) throws ServiceException;
    List<PageData> queryJcfwmx(PageData pd) throws ServiceException;
}
