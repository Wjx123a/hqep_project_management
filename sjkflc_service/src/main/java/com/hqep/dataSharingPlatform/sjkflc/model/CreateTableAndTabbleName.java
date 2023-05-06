package com.hqep.dataSharingPlatform.sjkflc.model;

import java.util.List;

public class CreateTableAndTabbleName {
    private String tableName;
    private List<CustomTable> customTables;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<CustomTable> getCustomTables() {
        return customTables;
    }

    public void setCustomTables(List<CustomTable> customTables) {
        this.customTables = customTables;
    }
}
