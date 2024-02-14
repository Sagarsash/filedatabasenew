package com.example.vertx;

import java.util.List;

public class InsertDataRequest {
    private String tableName;
    private List<Object> values;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}
