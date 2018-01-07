package com.pj.io.netty.chapter04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class NettyTimeServerHandler extends ChannelHandlerAdapter{

    private int countor;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         添加解码器之后，可以直接转换成字符串

        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String body = new String(bytes, "UTF-8");
         */

        String body = (String) msg;
        System.out.println("【" +Thread.currentThread().getName()
                + "】接收到消息：" + body + "; countor :"+ ++countor);
        String response = body.equals("ServerTime") ?
                new Date().toString() : "Bad ServerTime";

        response = response + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
