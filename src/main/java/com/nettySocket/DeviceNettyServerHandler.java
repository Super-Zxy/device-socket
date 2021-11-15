package com.nettySocket;

import com.config.LampblackConstant;
import com.entity.DeviceLampblackData;
import com.service.DeviceService;
import com.util.CRC16;
import com.util.StringUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxy
 * @date 2021/11/3 19:22
 * @description
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class DeviceNettyServerHandler extends SimpleChannelInboundHandler<String> {

    SimpleDateFormat ymdhmsSdf = new SimpleDateFormat("yyyyMMddHHmmss");

    SimpleDateFormat ymSdf = new SimpleDateFormat("yyyyMM");

    @Autowired
    private DeviceService deviceService;

    @Value("${deviceDbAndTable.dbName}")
    private String dbName;

    @Value("${deviceDbAndTable.tableName}")
    private String tableName;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        //打印出客户端地址
        log.info("IP：" + ctx.channel().remoteAddress() + "，油烟socket服务端收到消息：" + message);
        //解析油烟监控设备数据
        DeviceLampblackData deviceLampblackData = new DeviceLampblackData();

        Date nowDate = new Date();
        //本月
        String currentYm = this.ymSdf.format(nowDate);
        deviceLampblackData.setLbDeviceDataTableName(this.tableName + "_" + currentYm);
//                    deviceLampblackData.setRemoteSocketAddress(String.valueOf(socket.getRemoteSocketAddress()));

        //根据;分隔所有参数，与LampBlackMap匹配获取参数值
//                    ConnectionThread.findDeviceValueBySplit(deviceLampblackData, message);

        //根据参数LampBlackMap获取报文中对应参数值
        this.findDeviceValueByKeyMap(deviceLampblackData, message);

        switch (deviceLampblackData.getCn()) {
            case "9021":
                //登录注册
                if ("1".equals(deviceLampblackData.getFlag())) {
                    StringBuffer loginReturnMsg = new StringBuffer();

                    StringBuffer loginCpStr=new StringBuffer();
                    loginCpStr.append("ST=").append(deviceLampblackData.getSt()).append(";");
                    loginCpStr.append("CN=9022;");
                    loginCpStr.append("PW=").append(deviceLampblackData.getPw()).append(";");
                    loginCpStr.append("MN=").append(deviceLampblackData.getMn()).append(";");
                    loginCpStr.append("Flag=0;");
                    loginCpStr.append("CP=&&QN=").append(deviceLampblackData.getQn()).append(";");
                    loginCpStr.append("Logon=1&&");

                    loginReturnMsg.append("##");
                    loginReturnMsg.append(CRC16.autoGenericCode(loginCpStr.length(),4));

                    loginReturnMsg.append(loginCpStr);

                    loginReturnMsg.append(CRC16.crc16(loginCpStr.toString()));

                    loginReturnMsg.append("\r\n");

                    log.info("IP：" + ctx.channel().remoteAddress() + "，油烟socket服务端，注册登录返回信息：" + loginReturnMsg);

                    ctx.channel().writeAndFlush(loginReturnMsg);
                }
                break;
            case "1011":
                //对时
                if ("1".equals(deviceLampblackData.getFlag())) {
                    StringBuffer getTimeReturnMsg = new StringBuffer();

                    StringBuffer getTimeCpStr=new StringBuffer();
                    getTimeCpStr.append("ST=").append(deviceLampblackData.getSt()).append(";");
                    getTimeCpStr.append("CN=1011;");
                    getTimeCpStr.append("PW=").append(deviceLampblackData.getPw()).append(";");
                    getTimeCpStr.append("MN=").append(deviceLampblackData.getMn()).append(";");
                    getTimeCpStr.append("CP=&&QN=").append(deviceLampblackData.getQn()).append(";");
                    getTimeCpStr.append("SystemTime=").append(ymdhmsSdf.format(new Date())).append("&&");

                    getTimeReturnMsg.append("##");
                    getTimeReturnMsg.append(CRC16.autoGenericCode(getTimeCpStr.length(),4));

                    getTimeReturnMsg.append(getTimeCpStr);

                    getTimeReturnMsg.append(CRC16.crc16(getTimeCpStr.toString()));

                    getTimeReturnMsg.append("\r\n");
                    log.info("IP：" + ctx.channel().remoteAddress() + "，油烟socket服务端，对时返回信息：" + getTimeReturnMsg);

                    ctx.channel().writeAndFlush(getTimeReturnMsg);
                }
                break;
            case "2011":
                //上传数据
                if (StringUtils.isNotEmpty(deviceLampblackData.getMn()) && !"0".equals(deviceLampblackData.getMn())) {
                    //入表
                    int nRet = this.deviceService.addDeviceLampBlackData(deviceLampblackData);

                    log.info("IP：" + ctx.channel().remoteAddress() + "，油烟socket服务端，设备："+deviceLampblackData.getMn()+"数据："+deviceLampblackData.getQn()+"入库结束：" + nRet);
                } else {
                    log.error("IP：" + ctx.channel().remoteAddress() + "，设备编号 MN 为空，不处理！！");
                }
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    //根据;分隔所有参数，与LampBlackMap匹配获取参数值
    private void findDeviceValueBySplit(DeviceLampblackData deviceLampblackData, String clientInputStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] deviceDataArray = clientInputStr.split(";");
        for (String deviceData : deviceDataArray) {
            String deviceDataKey = deviceData.substring(0, deviceData.indexOf("="));
            String deviceDataKeyParam = LampblackConstant.deviceParams.get(deviceDataKey);
            String deviceDataValue = deviceData.substring(deviceData.indexOf("=") + 1);
            Class<?> clazz = deviceLampblackData.getClass();
            Method method = clazz.getMethod("set" + captureName(deviceDataKeyParam), String.class);
            method.invoke(deviceLampblackData, StringUtils.isNotEmpty(deviceDataValue) ? deviceDataValue : "0");
        }
    }

    //根据参数LampBlackMap获取报文中对应参数值
    private void findDeviceValueByKeyMap(DeviceLampblackData deviceLampblackData, String clientInputStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //删除##0030头,以;分隔数据
        String tmpStr=clientInputStr.substring(6);
        String[] clinetInputArray=tmpStr.split("CP=&&");
        clientInputStr=";"+clinetInputArray[0];
        String[] clinetInputCPArray=clinetInputArray[1].split("&&",-1);
        if(StringUtils.isNotEmpty(clinetInputCPArray[0])){
            clientInputStr+=clinetInputCPArray[0]+";";
        }

        //获取MN设备编号
        String[] strMnArray = clientInputStr.split(";MN=");
        String mn="0";
        if (strMnArray.length > 1) {
            String mnStrTmp = strMnArray[1];
            mn = mnStrTmp.substring(0, mnStrTmp.indexOf(";"));
        }
        deviceLampblackData.setMn(mn);
        //设备编号为0,则直接退出
        if("0".equals(mn))
        {
            return;
        }


        String deviceType="default-device";
        //根据mn号获取设备类型
        for (Map.Entry<String, String> entry : LampblackConstant.deviceMnMap.entrySet()) {
            String deviceTypeTmp=entry.getKey();
            String deviceMns=entry.getValue();
            if(StringUtils.isNotEmpty(deviceMns)&&deviceMns.indexOf(mn)>-1){
                deviceType=deviceTypeTmp;
                break;
            }
        }

        //根据设备类型获取设备参数列表
        HashMap<String,String> deviceParams=LampblackConstant.deviceTypeParamMap.get(deviceType);

        //根据参数列表设置数据值
        for (Map.Entry<String, String> entry : deviceParams.entrySet()) {
            String deviceDataKey = entry.getKey();
            String deviceDataKeyParam = entry.getValue();
            String[] strTmpArray = clientInputStr.split(";"+deviceDataKey + "=");
            String deviceDataValue = "0";
            if (strTmpArray.length > 1) {
                String strTmp = strTmpArray[1];
                deviceDataValue = strTmp.substring(0, strTmp.indexOf(";"));
            }
            Class<?> clazz = deviceLampblackData.getClass();
            Method method = clazz.getMethod("set" + captureName(deviceDataKeyParam), String.class);
            method.invoke(deviceLampblackData, StringUtils.isNotEmpty(deviceDataValue) ? deviceDataValue : "0");
        }
    }

    //首字母大写
    private String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
