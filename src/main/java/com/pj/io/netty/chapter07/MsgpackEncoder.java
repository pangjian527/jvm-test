package com.pj.io.netty.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 定义编码器
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object>{

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext
            , Object o, ByteBuf byteBuf) throws Exception {

        System.out.println("开始编码");
        MessagePack pack = new MessagePack();

        byte[] bytes = pack.write(o);
        byteBuf.writeBytes(bytes);
        System.out.println("编码完成");
    }
}
