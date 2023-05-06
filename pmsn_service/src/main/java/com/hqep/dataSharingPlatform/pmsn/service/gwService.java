package com.hqep.dataSharingPlatform.pmsn.service;


import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.model.Gwthree;


import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Map;

public interface gwService {


    //总部同步省侧数据上传需求申请接口
    boolean insert(Map map1) throws ServiceException;
    //总部同步省侧附件接口
    boolean insertfile(Map map1) throws ServiceException;
    //接收省侧数据上传处理日志接口
    Map selectdate() throws ServiceException;
    //接收省侧数据上传处理附件信息
    Map selectfile() throws ServiceException;

}
