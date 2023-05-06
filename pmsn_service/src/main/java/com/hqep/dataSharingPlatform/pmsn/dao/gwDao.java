package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.model.Gwmodel;
import com.hqep.dataSharingPlatform.pmsn.model.Gwthree;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface gwDao {


    /**
     * 总部同步省侧数据上传需求申请接口
     * @param map1
     * @return
     */
    boolean insert(Map map1);

    /**
     * 总部同步省侧附件接口
     * @param map1
     * @return
     */
    boolean insertfile(Map map1);

    /**
     * 接收省侧数据上传处理日志接口
     * @param
     * @return
     */
    Map selectdate();

    /**
     * 接收省侧数据上传处理日志接口
     * @param
     * @return
     */
    Map selectfile();
}
