package com.hqep.dataSharingPlatform.common.multipleData;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class sssjlDynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return sssjlDynamicDataSourceHolder.getDataSource();
    }
}
