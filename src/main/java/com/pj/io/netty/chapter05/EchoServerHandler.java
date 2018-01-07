package com.pj.io.netty.chapter05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

    int countor = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String body = (String) msg;

        System.out.println("【"+Thread.currentThread().getName() + "】服务端接收到请求信息："+ body+ "; countor:"+ ++countor);

        Thread.sleep(1000*60);
        body += "$_";
        ByteBuf byteBuf = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
