package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import java.util.List;

public interface QueryGdService {

    List<PageData> queryGdCount(PageData pd);

    List<PageData> queryrowClickOne(PageData pd);

    int queryrowClickOneCount(PageData pd);

    int queryGdCountCount(PageData pd);

    List<PageData> querySpGd(PageData pd);

    List<PageData> queryProgressToAudit(PageData pd);

    void updatePullWorkOrderForRevoke(PageData pd);
}



















