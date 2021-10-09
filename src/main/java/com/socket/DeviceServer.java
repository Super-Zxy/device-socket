//package com.socket;
//
//import com.service.DeviceService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author zxy
// * @date 2021/10/1 19:32
// * @description
// */
//@Component
//public class DeviceServer implements CommandLineRunner {
//
//    private static String tag = "DeviceServer====";
//
//    /**
//     * 日志
//     */
//    private final static Logger logger = LoggerFactory.getLogger(DeviceServer.class);
//
//    @Autowired
//    private DeviceService deviceService;
//
//    //监听的端口号
//    @Value("${socket.port}")
//    public int port = 8068;
//    //线程池 - 保持线程数 20
//    @Value("${socket.pool-keep}")
//    public int poolKeep = 20;
//    //线程池 - 核心线程数 10
//    @Value("${socket.pool-core}")
//    public int poolCore = 10;
//    //线程池 - 最大线程数 20
//    @Value("${socket.pool-max}")
//    public int poolMax = 20;
//    //线程队列容量 10
//    @Value("${socket.pool-queue-init}")
//    public int poolQueueInit = 10;
//
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            logger.info(tag + "油烟设备数据信息服务启动..." + port + "\n");
//            ServerSocket serverSocket = new ServerSocket(port);
//            ThreadPoolExecutor pool = new ThreadPoolExecutor(
//                    this.poolCore,
//                    this.poolMax,
//                    this.poolKeep,
//                    TimeUnit.SECONDS,
//                    new ArrayBlockingQueue<Runnable>(this.poolQueueInit),
//                    new ThreadPoolExecutor.DiscardOldestPolicy()
//            );
//            while (true) {
//                // 一旦有堵塞, 则表示服务器与客户端获得了连接
//                Socket client = serverSocket.accept();
////                client.setKeepAlive(true);
//                // 处理这次连接
//                pool.execute(new DeviceHandlerThread(client, deviceService));
//            }
//        } catch (Exception e) {
//            logger.error(tag + "油烟设备数据信息服务异常: " + e.getMessage());
//        }
//    }
//}
