package com.nettySocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author zxy
 * @date 2021/11/3 19:18
 * @description
 */
@Component
public class DeviceNettyServer implements CommandLineRunner {
    private static String tag = "DeviceNettyServer====";

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(DeviceNettyServer.class);

    //监听的端口号
    @Value("${socket.port}")
    public int port = 8068;

    @Autowired
    private DeviceNettyServerHandler deviceNettyServerHandler;

    @Autowired
    private ServerHandler serverHandler;

    @Override
    public void run(String... args) throws Exception {
        logger.info(tag+"油烟服务启动成功。。。。端口：："+this.port);

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //在服务器端的handler()方法表示对bossGroup起作用，而childHandler表示对wokerGroup起作用
            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
                    // 有数据立即发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            // 增加任务处理
                            ChannelPipeline p = sc.pipeline();
                            p.addLast(new DecoderHandler(), // 自定义解码器
                                    //默认的编码器
                                    new StringEncoder(Charset.forName("utf-8")),
                                    new StringDecoder(Charset.forName("utf-8")),
                                    // 自定义的处理器
                                    // new ServerHandler()
                                    deviceNettyServerHandler);
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
}
