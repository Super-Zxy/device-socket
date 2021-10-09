package com.service.impl;

import com.dao.DeviceMapper;
import com.entity.DeviceLampblackData;
import com.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zxy
 * @date 2021/10/1 23:47
 * @description
 */
@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public int addDeviceLampBlackData(DeviceLampblackData deviceLampblackData) {

        int nRet=deviceMapper.insertDeviceLampBlackData(deviceLampblackData);
        return nRet;
    }
}
