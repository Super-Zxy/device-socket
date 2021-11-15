package com.nettySocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxy
 * @date 2021/11/4 13:24
 * @description
 */
public class DecoderHandler extends ByteToMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(DecoderHandler.class);

    private static Map<ChannelHandlerContext, String> msgBufMap = new ConcurrentHashMap<>();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);
        String msg = new String(data, Charset.forName("utf-8"));
        // 处理粘包拆包问题
        if (msg.startsWith("#")) {
            msg=msg.replaceAll("\r\n","");
            out.add(msg);
        }
    }
}
