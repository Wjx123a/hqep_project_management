package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 全量表查询
 */
@Repository
@sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
public interface QueryGdDao {

    List<PageData> queryGdCount(PageData pd);

    void addGd(PageData param);

    List<PageData> queryrowClickOne(PageData pd);

    void updateSpZt(PageData param);

    void updatePullWorkOrderForRevoke(PageData param);

   void updateSpZtByGXCXTY(PageData param);

    void updateSpZtBh(PageData logData);

    void delShoppingCart(PageData logData);

    int queryrowClickOneCount(PageData pd);

    int queryGdCountCount(PageData pd);

    List<PageData> querySpGd(PageData pd);

    List<PageData> queryProgressToAudit(PageData pd);

    /**
     * @Author hanyf
     * @Description //添加同步国网标记
     * @Date 15:25 2021/11/18
     * @Param [pd]
     * @return void
     **/
    void updateWorkOrder(PageData pd);
    void updateWorkOrder_new(PageData pd);
}



















