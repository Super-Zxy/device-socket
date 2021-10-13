package com.dao;

import com.config.DataSourceAnnotation;
import com.entity.DeviceLampblackData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zxy
 * @date 2021/10/1 23:48
 * @description
 */
public interface DeviceMapper {

    @DataSourceAnnotation(tableName = "lb_device_data")
    public int insertDeviceLampBlackData(DeviceLampblackData deviceLampblackData);

    @DataSourceAnnotation(tableName = "lb_device_data")
    public int createLBDataTableAndIndex(@Param("lbDataTableName") String lbDataTableName);

    @DataSourceAnnotation(tableName = "lb_device_data")
    public List qryTableByTableName(@Param("dbName") String dbName, @Param("tableName") String tableName);

    @DataSourceAnnotation(tableName = "lb_device_data")
    public int dropTable(@Param("tableName") String tableName);
}
