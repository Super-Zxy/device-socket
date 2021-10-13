package com.service;

import com.entity.DeviceLampblackData;

public interface DeviceService {

    public int addDeviceLampBlackData(DeviceLampblackData deviceLampblackData);

    public int createLBDataTableAndIndex(String dbName, String lbDataTableName);

    public int dropTable(String dbName,String tableName);
}
