package com.device.utils.socket.server;

import com.config.LampblackConstant;
import com.device.utils.socket.dto.ServerReceiveDto;
import com.entity.DeviceLampblackData;
import com.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

/**
 * 每一个client连接开一个线程
 *
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

@Slf4j
@Data
public class ConnectionThread extends Thread {

    /**
     * 客户端的socket
     */
    private Socket socket;

    /**
     * 服务socket
     */
    private SocketServer socketServer;

    /**
     * 封装的客户端连接socket
     */
    private Connection connection;

    /**
     * 判断当前连接是否运行
     */
    private boolean isRunning;

    public ConnectionThread(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        this.socketServer = socketServer;
        connection = new Connection(socket, this);
        Date now = new Date();
        connection.setCreateTime(now);
        connection.setLastOnTime(now);
        isRunning = true;
    }

    @Override
    public void run() {
        log.info("油烟监控设备客户 - " + socket.getRemoteSocketAddress() + " -> 机连接成功");
        while (isRunning) {
            // Check whether the socket is closed.
            if (socket.isClosed()) {
                isRunning = false;
                break;
            }
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    log.info("IP："+ socket.getRemoteSocketAddress()+"，油烟socket服务端收到消息：" + message);

                    //解析油烟监控设备数据
                    DeviceLampblackData deviceLampblackData = new DeviceLampblackData();

                    Date nowDate=new Date();
                    //本月
                    String currentYm=this.socketServer.ymSdf.format(nowDate);
                    deviceLampblackData.setLbDeviceDataTableName(this.socketServer.getTableName()+"_"+currentYm);

//                    deviceLampblackData.setRemoteSocketAddress(String.valueOf(socket.getRemoteSocketAddress()));

                    //根据;分隔所有参数，与LampBlackMap匹配获取参数值
//                    ConnectionThread.findDeviceValueBySplit(deviceLampblackData, message);

                    //根据参数LampBlackMap获取报文中对应参数值
                    ConnectionThread.findDeviceValueByKeyMap(deviceLampblackData,message);

                    if(StringUtils.isNotEmpty(deviceLampblackData.getMn())&&!"0".equals(deviceLampblackData.getMn())) {
                        //入表
                        int nRet = this.socketServer.getDeviceService().addDeviceLampBlackData(deviceLampblackData);

                        log.info("IP："+ socket.getRemoteSocketAddress()+"，油烟socket服务端，数据入库结束：" + nRet);
                    }else{
                        log.error("IP："+ socket.getRemoteSocketAddress()+"，设备编号 MN 为空，不处理！！");
                    }
                    break;
                }
                //处理结束停止socket
                this.stopRunning();
            } catch (Exception e) {
                log.error("IP："+ socket.getRemoteSocketAddress()+"，ConnectionThread.run failed. Exception:{}", e);
                this.stopRunning();
            }
        }
    }

    public void stopRunning() {
        log.info("停止一个socket连接,ip:{},userId:{}", this.socket.getRemoteSocketAddress().toString(),
                this.connection.getUserId());
        isRunning = false;
        socketServer.getExistConnectionThreadList().remove(this);
        try {
            socket.close();
        } catch (IOException e) {
            log.error("ConnectionThread.stopRunning failed.exception:{}", e);
        }
    }

    //根据;分隔所有参数，与LampBlackMap匹配获取参数值
    private static void findDeviceValueBySplit(DeviceLampblackData deviceLampblackData, String clientInputStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
    private static void findDeviceValueByKeyMap(DeviceLampblackData deviceLampblackData, String clientInputStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Map.Entry<String, String> entry : LampblackConstant.deviceParams.entrySet()) {
            String deviceDataKey = entry.getKey();
            String deviceDataKeyParam = entry.getValue();
            String[] strTmpArray = clientInputStr.split(deviceDataKey + "=");
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
    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}