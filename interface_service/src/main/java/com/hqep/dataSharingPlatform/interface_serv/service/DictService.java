package com.hqep.dataSharingPlatform.interface_serv.service;

import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2021/03/20$ 9:42$
 * @Version: 1.0
 */
public interface DictService {

    Map getUserNameList(Map map);

    Map getSystemNameList(Map map);

    /**
     * 下发订单管理
     * 查询系统名列表/dict/getDownOrderSystemNameList
     *
     * @return
     */
    Map getDownOrderSystemNameList(Map map);

    Map getDeptNameList(Map map);

    Map getDownOrderDeptNameList(Map map);

    Map getCountOfSjzt(Map map);

    Map getSystemNameListForFmqd(Map map);

    Map getUserNameListForFmqd(Map map);
}
