package com.config;

import com.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author zxy
 * @date 2021/6/28 17:34
 * @description
 */
@Aspect
@Component
public class DataSourceAspect {

    //joinPoint用于获取域切入点方法有关的信息
    @Before(value = "@annotation(dataSourceAnnotation)")
    public void doBefore(JoinPoint joinPoint, DataSourceAnnotation dataSourceAnnotation) {
        String tableName = dataSourceAnnotation.tableName();
        String dataSourceName = dataSourceAnnotation.dataSourceName();
        String dataSourceWeight = dataSourceAnnotation.dataSourceWeight();

        //设置数据源
        if (dataSourceWeight.equals(DataSourceConstant.DB_NAME_WEIGHT) && StringUtils.isNotEmpty(dataSourceName)) {
            //当设置数据源权重为数据库名时，根据数据库名设置数据源
            DynamicDataSourceContextHolder.setDataSourceKey(dataSourceName);

        } else if (StringUtils.isNotEmpty(tableName)) {
            //表名不为空时，根据表名设置数据源
            String dbRule = DataSourceConstant.tableDbRuleMap.get(tableName);
            DynamicDataSourceContextHolder.setDataSourceKey(dbRule);
        } else {
            //默认数据源
            DynamicDataSourceContextHolder.setDataSourceKey(DataSourceConstant.LAMPBLACK_DB);
        }
    }
}
