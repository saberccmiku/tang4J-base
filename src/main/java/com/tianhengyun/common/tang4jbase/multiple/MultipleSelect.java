package com.tianhengyun.common.tang4jbase.multiple;

import java.util.Map;

public class MultipleSelect {
    //select all columns what you want
    private String columns;
    //the from table name

    public String createMultipleSql(Map<String, Object> map) {
        Multiple[] multiples = (Multiple[]) map.get("multiples");
        return MultipleFactory.makeSelect(multiples);
    }
}
