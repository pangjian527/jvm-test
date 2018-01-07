package com.pj.io.netty.chapter07;

import com.pj.io.netty.chapter07.entity.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * 事件处理Handler
 */
public class MessagePackEchoServerHandler extends ChannelHandlerAdapter {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        List<UserInfo> userInfos = (List<UserInfo>) msg;
        System.out.println("【"+ Thread.currentThread().getName() +"】服务端收到消息对象长度：" + userInfos.toString());

        ctx.writeAndFlush(userInfos);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
