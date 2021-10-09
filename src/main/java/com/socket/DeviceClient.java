package com.socket;

import java.io.*;
import java.net.Socket;

/**
 * @author zxy
 * @date 2021/10/1 19:35
 * @description
 */
public class DeviceClient {
    public static final String IP_ADDR = "localhost";//服务器地址
    public static final int PORT = 8068;//服务器端口号
//    public static final String IP_ADDR = "123.60.23.225";//服务器地址

//    public static final String IP_ADDR = "121.37.178.53";//服务器地址
//    public static final String IP_ADDR = "4409s41e56.qicp.vip";//服务器地址
//    public static final int PORT = 23137;//服务器端口号

    public static void main(String[] args) {
         System.out.println("客户端启动...");
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
//        while (true) {
            Socket socket = null;
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(IP_ADDR, PORT);

//                //读取服务器端数据
//                DataInputStream input = new DataInputStream(socket.getInputStream());
//                //向服务器端发送数据
//                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//                while(true){
//                    System.out.print("请输入: \t");
////                String str = "MN=123456;DataTime=20190312113200001;a01011-Rtd=11;a01012-Rtd=22;a01013-Rtd=33;a01014-Rtd=44;a01017-Rtd=55;a05024-Rtd=66;a19002-Rtd=77;a24088-Rtd=88;a34000-Rtd=99;a34041-Rtd=100;ga0601-Rtd=110;ga0611-Rtd=120;ga0701-Rtd=130;ga0801-Rtd=140;ga2001-Rtd=150;ga2011-Rtd=160;ga2101-Rtd=170;ga2201-Rtd=180;";
//                    String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
//                    out.writeUTF(str);
//                }
                OutputStream out=socket.getOutputStream();


//                String ret = input.readUTF();
//                System.out.println("服务器端返回过来的是: " + ret);
//                // 如接收到 "OK" 则断开连接
//                if ("OK".equals(ret)) {
//                    System.out.println("客户端将关闭连接");
//                    Thread.sleep(500);
////                    break;
//                }

//                out.close();
//                input.close();
            } catch (Exception e) {
                System.out.println("客户端异常:" + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                        System.out.println("socket is closed");
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("客户端 finally 异常:" + e.getMessage());
                    }
                }
            }
//        }
    }
}
