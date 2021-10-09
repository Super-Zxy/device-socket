package com.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zxy
 * @date 2021/6/28 16:45
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceAnnotation {

    String tableName() default "";

    String dataSourceName() default DataSourceConstant.LAMPBLACK_DB;

    String dataSourceWeight() default DataSourceConstant.TABLE_NAME_WEIGHT;

}
