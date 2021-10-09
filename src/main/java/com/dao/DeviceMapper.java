package com.dao;

import com.config.DataSourceAnnotation;
import com.entity.DeviceLampblackData;

/**
 * @author zxy
 * @date 2021/10/1 23:48
 * @description
 */
public interface DeviceMapper {

    @DataSourceAnnotation(tableName = "lb_device_lampblack_data")
    public int insertDeviceLampBlackData(DeviceLampblackData deviceLampblackData);

}
