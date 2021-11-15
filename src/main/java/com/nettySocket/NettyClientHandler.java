package com.nettySocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @author zxy
 * @date 2021/11/3 20:20
 * @description
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    String loginMsg="QN=20121231010101001;ST=91;CN=9021;PW=123456;MN=52065030012341;Flag=1;CP=&&&&";
    String getTimeMsg="QN=20121231010101001;ST=31;CN=1011;PW=123456;MN=52065030012341;Flag=1;CP=&&&&";
    String sendMsg="##0181QN=20211011123600000;ST=51;CN=2011;PW=123456;MN=52065030012341;Flag=5;CP=&&DataTime=20211011123600000;ga2101-Rtd=1;ga0701-Rtd=1;a34041-Rtd=2.04;a34000-Rtd=1.41;a24088-Rtd=5.67;&&?A";


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //服务端的远程地址
        System.out.println("client output: "+msg);
        Thread.sleep(3000);
        ctx.writeAndFlush(getTimeMsg);
        Thread.sleep(3000);
        ctx.writeAndFlush(sendMsg);
    }

    /**
     * 当服务器端与客户端进行建立连接的时候会触发，如果没有触发读写操作，则客户端和客户端之间不会进行数据通信，也就是channelRead0不会执行，
     * 当通道连接的时候，触发channelActive方法向服务端发送数据触发服务器端的handler的channelRead0回调，然后
     * 服务端向客户端发送数据触发客户端的channelRead0，依次触发。
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(loginMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
