package com.hqep.dataSharingPlatform.interface_serv.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/18$ 10:24$
 * @Version: 1.0
 */
@Repository
public interface PullNewDataDao {
    /**
     * 根据主键自动选择插入还是更新用户数据
     *
     * @param result 多条用户记录
     * @return 受到影响的记录数（PS:更新原理：先删后插，所以影响记录数为2）
     */
    int replaceMoreUsers(List<Map> result);

    /**
     * 根据主键自动选择插入还是更新字典数据
     *
     * @param result 多条用户记录
     * @return 受到影响的记录数（PS:更新原理：先删后插，所以影响记录数为2）
     */
    int replaceMoreColumns(List<Map> result);
}
