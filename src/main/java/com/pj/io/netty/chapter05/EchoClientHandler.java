package com.pj.io.netty.chapter05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter{

    String message = "ServerTime.$_";;
    int countor = 0;

    public EchoClientHandler() {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("【"+Thread.currentThread().getName()+"】开始发送数据...");
        for (int i=0;i<10;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(message.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("客户端收到回复："+ body + "; countor:"+ ++countor);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
