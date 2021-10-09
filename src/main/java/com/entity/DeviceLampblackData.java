package com.entity;

import lombok.Data;

/**
 * @author zxy
 * @date 2021/10/1 23:00
 * @description
 */
@Data
public class DeviceLampblackData {
    //创建时间
    private String createTime;
    //客户机IP
    private String remoteSocketAddress;
    //检测单位号
    private String mn;
    //数据时间
    private String dateTime;
    //烟气流速
    private String lampblackSpeed;
    //烟气温度
    private String lampblackTemperature;
    //烟气压力
    private String lampblackPressure;
    //烟气湿度
    private String lampblackHumidity;
    //烟气动压
    private String lampblackDP;
    //臭氧
    private String ozone;
    //臭氧浓度
    private String ozoneConcentration;
    //非甲烷总烃
    private String nmhc;
    //颗粒物
    private String pm;
    //油烟
    private String lampblack;
    //净电净化设备工作电流
    private String purificationDeviceA;
    //净电净化设备工作电压
    private String purificationDeviceV;
    //净电净化设备工作状态
    private String purificationDeviceStatus;
    //净电净化设备累计耗电量
    private String purificationDeviceTotalW;
    //排风机工作电流
    private String exhaustFanA;
    //排风机工作电压
    private String exhaustFanV;
    //排风机工作状态
    private String exhaustFanStatus;
    //排风机累计耗电量
    private String exhaustFanTotalW;

}
