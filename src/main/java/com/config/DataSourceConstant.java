package com.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxy
 * @date 2021/6/28 17:16
 * @description
 */
public class DataSourceConstant {
    //数据源
    public static final String LAMPBLACK_DB = "LAMPBLACK_DB";
    public static final String DB11_SUB_SYS_BOSSPUB = "DB11_SUB_SYS_BOSSPUB";

    //数据源规则权重
    public static final String TABLE_NAME_WEIGHT = "TABLE_NAME_WEIGHT";
    public static final String DB_NAME_WEIGHT = "DB_NAME_WEIGHT";

    //表数据源映射关系
    public static Map<String,String> tableDbRuleMap=new HashMap<>();
}
