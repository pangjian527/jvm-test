package com.pj.io.netty.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf>{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("开始解码");
        int length = byteBuf.readableBytes();
        byte[] bytes = new byte[length];

        byteBuf.getBytes(byteBuf.readerIndex(),bytes,0,length);
        MessagePack pack = new MessagePack();

        list.add(pack.read(bytes));
        System.out.println("解码完成");
    }
}
