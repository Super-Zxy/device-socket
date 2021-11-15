package com.device.utils.socket.server;

import com.device.utils.socket.handler.LoginHandler;
import com.device.utils.socket.handler.MessageHandler;
import com.service.DeviceService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

@Data
@Component
public class SocketServer {

    private static String tag = "SocketServer====";

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(SocketServer.class);

    SimpleDateFormat ymSdf = new SimpleDateFormat("yyyyMM");

    @Autowired
    private DeviceService deviceService;

    //监听的端口号
    @Value("${socket.port}")
    public int port = 8068;
    //线程池 - 保持线程数 20
    @Value("${socket.pool-keep}")
    public int poolKeep = 20;
    //线程池 - 核心线程数 10
    @Value("${socket.pool-core}")
    public int poolCore = 10;
    //线程池 - 最大线程数 20
    @Value("${socket.pool-max}")
    public int poolMax = 20;
    //线程队列容量 10
    @Value("${socket.pool-queue-init}")
    public int poolQueueInit = 10;

    @Value("${deviceDbAndTable.dbName}")
    private String dbName;

    @Value("${deviceDbAndTable.tableName}")
    private String tableName;


    private ServerSocket serverSocket;

    /**
     * 服务监听主线程
     */
    private ListeningThread listeningThread;

    /**
     * 消息处理器
     */
    private MessageHandler messageHandler;

    /**
     * 登陆处理器
     */
    private LoginHandler loginHandler;

    /**
     * 用户扫已有的socket处理线程
     * 1. 没有的线程不引用
     * 2. 关注是否有心跳
     * 3. 关注是否超过登陆时间
     */
    private ScheduledExecutorService scheduleSocketMonitorExecutor = Executors
            .newSingleThreadScheduledExecutor(r -> new Thread(r, "socket_monitor_" + r.hashCode()));

    /**
     * 存储只要有socket处理的线程
     */
    private List<ConnectionThread> existConnectionThreadList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 中间list，用于遍历的时候删除
     */
    private List<ConnectionThread> noConnectionThreadList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 存储当前由用户信息活跃的的socket线程
     */
    private ConcurrentMap<String, Connection> existSocketMap = new ConcurrentHashMap<>();

    public SocketServer() {

    }

    /**
     * 开一个线程来开启本地socket服务，开启一个monitor线程
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error("本地socket服务启动失败.exception:{}", e);
        }
        listeningThread = new ListeningThread(this);
        listeningThread.start();
//        //每隔1s扫一次ThreadList
//        scheduleSocketMonitorExecutor.scheduleWithFixedDelay(() -> {
//            System.out.println("before---existConnectionThreadList="+existConnectionThreadList);
//            System.out.println("before---noConnectionThreadList="+noConnectionThreadList);
//            Date now = new Date();
//            //删除list中没有用的thread引用
//            existConnectionThreadList.forEach(connectionThread -> {
//                if (!connectionThread.isRunning()) {
//                    noConnectionThreadList.add(connectionThread);
//                }
//            });
//            noConnectionThreadList.forEach(connectionThread -> {
//                existConnectionThreadList.remove(connectionThread);
//                this.existSocketMap.remove(connectionThread.getConnection().getUserId());
//            });
//            noConnectionThreadList.clear();
//            System.out.println("after---existConnectionThreadList="+existConnectionThreadList);
//            System.out.println("after---noConnectionThreadList="+noConnectionThreadList);
//
//        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 关闭本地socket服务
     */
    public void close() {
        try {
            //先关闭monitor线程，防止遍历list的时候
            scheduleSocketMonitorExecutor.shutdownNow();
            if (serverSocket != null && !serverSocket.isClosed()) {
                listeningThread.stopRunning();
                listeningThread.suspend();
                listeningThread.stop();

                serverSocket.close();
            }
        } catch (IOException e) {
            logger.error("SocketServer.close failed.exception:{}", e);
        }
    }

//    @Override
//    public void run(String... args) throws Exception {
//        this.setMessageHandler((connection, receiveDto) -> logger
//                .info("处理socket消息,userId:{},receiveDto:{}", connection.getUserId(),
//                        receiveDto.getMessage()));
//        this.start();
//    }
}