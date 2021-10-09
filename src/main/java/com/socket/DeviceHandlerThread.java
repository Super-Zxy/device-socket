package com.socket;

import com.config.LampblackConstant;
import com.entity.DeviceLampblackData;
import com.service.DeviceService;
import com.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import static java.awt.SystemColor.info;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */
public class DeviceHandlerThread implements Runnable {

    private static String tag = "DeviceHandlerThread====";

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(DeviceHandlerThread.class);

    private DeviceService deviceService;

    private Socket socket;

    public DeviceHandlerThread(Socket client, DeviceService deviceService) {
        this.socket = client;
        this.deviceService = deviceService;
    }

    public void run() {
        try {
            socket.setSoTimeout(10000);
            logger.debug(tag + "油烟监控设备客户 - " + socket.getRemoteSocketAddress() + " -> 机连接成功");
            // 读取客户端数据

////            String clientInputStr="";
//            InputStream inputStream = socket.getInputStream();
////            Scanner in=new Scanner(inputStream);
////            String line;
//            byte[] cbuf = new byte[8096];
//            byte[] bytes = new byte[1024];
//
////            DataInputStream input = new DataInputStream(socket.getInputStream());
////            input.read(cbuf);
//            while (true) {
//                try {
//                    int len = inputStream.read(bytes);
//                    if (len != -1) {
//                        StringBuffer request = new StringBuffer();
//                        request.append(new String(bytes, 0, len, "UTF-8"));
//
////                String clientInputStr=new String(cbuf);
//                        if (StringUtils.isNotEmpty(request + "")) {
//                            logger.debug("读取到的客户端String是：" + request);
//                        }
//                    }
//                } catch (Exception e) {
//                    logger.error(tag + "油烟数据Socket服务器 run 异常: " + e.getMessage());
//                }
//            }

//            while(true){
//                // 3.通过Socket管道得到一个字节输入流
//                InputStream is = socket.getInputStream();
//                // 4.将字节输入流包装成一个高级的缓冲字符输入流
//                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                // 5.通过缓冲字符输入流接收客户端的数据
//                String line;
//                while((line = br.readLine()) != null) {
//                    System.out.println(socket.getRemoteSocketAddress() + "发来数据：" + line);
//                }
//                Thread.sleep(5000);
//            }


            while(true){
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                // 处理客户端数据
                logger.debug(tag + "油烟监控设备数据信息:" + clientInputStr);
                Thread.sleep(2000);
            }

//            //解析油烟监控设备数据
//            DeviceLampblackData deviceLampblackData = new DeviceLampblackData();
//
//            deviceLampblackData.setRemoteSocketAddress(String.valueOf(socket.getRemoteSocketAddress()));
//
//            //根据;分隔所有参数，与LampBlackMap匹配获取参数值
//            DeviceHandlerThread.findDeviceValueBySplit(deviceLampblackData, clientInputStr);
//
//            //根据参数LampBlackMap获取报文中对应参数值
////            DeviceHandlerThread.findDeviceValueByKeyMap(deviceLampblackData,clientInputStr);
//
//            //入表
//
//            int nRet = deviceService.addDeviceLampBlackData(deviceLampblackData);

//            input.close();
        } catch (Exception e) {
            logger.error(tag + "油烟数据Socket服务器 run 异常: " + e.getMessage());
        }
//        finally {
//            if (socket != null) {
//                try {
//                    socket.close();
//                } catch (Exception e) {
//                    socket = null;
//                    logger.error(tag + "油烟数据Socket服务端 finally 异常:" + e.getMessage());
//                }
//            }
//        }
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
