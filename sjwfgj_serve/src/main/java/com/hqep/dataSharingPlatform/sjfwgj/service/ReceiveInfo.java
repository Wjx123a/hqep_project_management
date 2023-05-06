package com.hqep.dataSharingPlatform.sjfwgj.service;

import com.hqep.dataSharingPlatform.sjfwgj.model.ReceiveModel;
import com.hqep.dataSharingPlatform.sjfwgj.pojo.ReceiveDO;
import com.hqep.dataSharingPlatform.sjfwgj.pojo.ReceiveDetailDO;

import javax.xml.rpc.ServiceException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: ReceiveInfo
 * @author: wjx
 * @data: 2023/5/1 18:03 PM
 */
public interface ReceiveInfo {
//    ReceiveModel insert(ReceiveModel receiveModel);
//    ReceiveDetailDO select();
    boolean insert(Map map1) throws ServiceException;
    boolean insertDetail(Map map1)throws ServiceException;
    Map select();
    List<Map> list();
    Map selectDetail();
    List<Map> listDetail();

}
