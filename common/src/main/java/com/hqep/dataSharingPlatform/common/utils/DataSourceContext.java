package com.hqep.dataSharingPlatform.common.utils;

public class DataSourceContext {

    public static final String DATA_SOURCE_1 = "mysqlDataSource1";
    public static final String DATA_SOURCE_2 = "mysqlDataSource2";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public DataSourceContext() {
    }

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return (String) contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }

}
