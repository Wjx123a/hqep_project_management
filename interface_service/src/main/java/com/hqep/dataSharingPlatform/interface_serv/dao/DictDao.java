package com.hqep.dataSharingPlatform.interface_serv.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2021/03/20$ 9:42$
 * @Version: 1.0
 */
@Repository
public interface DictDao {

    List getUserNameList(Map map);

    List getSystemNameList(Map map);

    List getDownOrderSystemNameList(Map map);

    List getDeptNameList(Map map);

    List getDownOrderDeptNameList(Map map);

    int getCountOfSjzt(Map map);

    List getSystemNameListForFmqd(Map map);

    List getUserNameListForFmqd(Map map);
}
