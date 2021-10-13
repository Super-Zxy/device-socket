package com.service.impl;

import com.dao.DeviceMapper;
import com.entity.DeviceLampblackData;
import com.service.DeviceService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxy
 * @date 2021/10/1 23:47
 * @description
 */
@Service
public class DeviceServiceImpl implements DeviceService{

    private static String tag = "DeviceServiceImpl====";

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);


    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public int addDeviceLampBlackData(DeviceLampblackData deviceLampblackData) {

        int nRet=deviceMapper.insertDeviceLampBlackData(deviceLampblackData);
        return nRet;
    }

    @Override
    public int createLBDataTableAndIndex(String dbName,String lbDataTableName) {
        int nRet=-1;
        List tableList=deviceMapper.qryTableByTableName(dbName,lbDataTableName);
        if(CollectionUtils.isEmpty(tableList)){
            logger.info(tag+"数据库："+dbName+"表："+lbDataTableName+"不存在，创建表。。。。");
            nRet=deviceMapper.createLBDataTableAndIndex(lbDataTableName);
        }else{
            logger.info(tag+"数据库："+dbName+"表："+lbDataTableName+"已存在");
        }
        return nRet;
    }

    @Override
    public int dropTable(String dbName,String tableName) {
        int nRet=-1;
        List tableList=deviceMapper.qryTableByTableName(dbName, tableName);
        if(CollectionUtils.isNotEmpty(tableList)){
            logger.info(tag+"清理2年前表--数据库："+dbName+"表："+tableName+"清理。。。。");
            nRet=deviceMapper.dropTable(tableName);
        }else{
            logger.info(tag+"2年前表--数据库："+dbName+"表："+tableName+"不存在");
        }
        return nRet;
    }


}
