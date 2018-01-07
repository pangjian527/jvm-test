package com.pj.io.netty.chapter07;

import com.pj.io.netty.chapter07.entity.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MessagePackEchoClientHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("客户端开始发送消息...");
        UserInfo[] userInfos = userInfos();

        for (UserInfo userInfo: userInfos) {
            ctx.write(userInfo);
        }
        ctx.flush();
        System.out.println("客户端开始发送完成");
    }

    private UserInfo[] userInfos(){
        UserInfo[] userInfos = new UserInfo[10];


        for (int i=0;i<userInfos.length ;i++){
            //List<String> items = Arrays.asList("abc"+i, "bcd"+i);
            UserInfo userInfo = new UserInfo(i,"ABCD"+i);
            userInfos[i] = userInfo;
        }

        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端接收到返回信息："+ msg);
    }

}
