package com.hqep.dataSharingPlatform.common.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        Object key = DataSourceContext.getDbType();
        if (key != null) {
            this.logger.info("当前线程使用的数据源标识为 [ " + key.toString() + " ].");
        }
        return key;
    }
}
