package com.hqep.dataSharingPlatform.sjfwgj.service.impl;

import com.hqep.dataSharingPlatform.sjfwgj.dao.ReceiveDOMapper;
import com.hqep.dataSharingPlatform.sjfwgj.dao.ReceiveDetailDOMapper;
import com.hqep.dataSharingPlatform.sjfwgj.model.ReceiveModel;
import com.hqep.dataSharingPlatform.sjfwgj.pojo.ReceiveDO;
import com.hqep.dataSharingPlatform.sjfwgj.pojo.ReceiveDetailDO;
import com.hqep.dataSharingPlatform.sjfwgj.service.ReceiveInfo;
import com.hqep.dataSharingPlatform.sjfwgj.utils.NumberAutoNo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: receiveInfoImpl
 * @author: wjx
 * @data: 2023/5/1 18:04 PM
 */

@Transactional
@Service
public class ReceInfoimpl implements ReceiveInfo {
    @Autowired
    private ReceiveDetailDOMapper receiveDetailDOMapper;
    @Autowired
    private ReceiveDOMapper receiveDOMapper;

    @Override
    public boolean insert(Map map1) throws ServiceException {
         return receiveDOMapper.insert(map1);
    }

    @Override
    public boolean insertDetail(Map map1) throws ServiceException {
        return receiveDetailDOMapper.insert(map1);
    }

    @Override
    public Map select() {
        return receiveDOMapper.select();
    }
    @Override
    public List<Map> list() {
        List<Map> receiveDOS = receiveDOMapper.selectlist();
        List<Map> collect = receiveDOS.stream().collect(Collectors.toList());
        return collect;
    }
    @Override
    public Map selectDetail() {
        return receiveDetailDOMapper.selectdetail();
    }

    @Override
    public List<Map> listDetail() {
        List<Map> receiveDOS = receiveDetailDOMapper.selectDetaillist();
        List<Map> collect = receiveDOS.stream().collect(Collectors.toList());
        return collect;
    }


//    @Override
//    public ReceiveModel insert(ReceiveModel receiveModel) {
//        //转化为pojo
//        ReceiveDO receiveDO = convertReceivePojoFromReceiveModel(receiveModel);
//        //写入数据库
//        receiveDOMapper.insertSelective(receiveDO);
//        receiveModel.setTicketId(receiveDO.getTicketId());
//        ReceiveDetailDO receiveDetailDO = convertReceiveDetailFromReceiveModel(receiveModel);
//        receiveDetailDOMapper.insertSelective(receiveDetailDO);
//        //返回创建model
//        return this.getItemByTicketId(receiveModel.getTicketId());
//    }

//    @Override
//    public ReceiveDetailDO select() {
//        ReceiveDetailDO receiveDetailDO = receiveDetailDOMapper.select();
//        return receiveDetailDO;
//    }
//
//    public ReceiveDO convertReceivePojoFromReceiveModel(ReceiveModel receiveModel){
//        if(receiveModel==null){
//            return null;
//        }
//        ReceiveDO receiveDO = new ReceiveDO();
//        BeanUtils.copyProperties(receiveModel,receiveDO);
//        return receiveDO;
//    }
//    public ReceiveDetailDO convertReceiveDetailFromReceiveModel(ReceiveModel receiveModel){
//        ReceiveDetailDO receiveDetailDO= new ReceiveDetailDO();
//        BeanUtils.copyProperties(receiveModel,receiveDetailDO);
//        receiveDetailDO.setTicketId(receiveModel.getTicketId());
//        return receiveDetailDO;
//    }
//    public ReceiveModel getItemByTicketId(String ticketId){
//        ReceiveDO receiveDO = receiveDOMapper.selectByPrimaryKey(ticketId);
//        if(receiveDO==null){
//            return null;
//        }
//        //获取小表
//        ReceiveDetailDO receiveDetailDO = receiveDetailDOMapper.selectByPrimaryKey(receiveDO.getTicketId());
//        //将pojo转化为model
//        ReceiveModel receiveModel = convertModelFromPojo(receiveDO,receiveDetailDO);
//        return receiveModel;
//    }
//    public ReceiveModel convertModelFromPojo(ReceiveDO receiveDO,ReceiveDetailDO receiveDetailDO){
//        ReceiveModel receiveModel = new ReceiveModel();
//        BeanUtils.copyProperties(receiveDO,receiveModel);
//        BeanUtils.copyProperties(receiveDetailDO,receiveModel);
//        return receiveModel;
//    }
}
