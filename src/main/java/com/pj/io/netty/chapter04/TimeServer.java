package com.pj.io.netty.chapter04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 粘包和拆包
 */
public class TimeServer {

    public void bind(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        System.out.println("【"+ Thread.currentThread().getName() +"】开始监听端口");

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChildChannelHandler());

        try{
            ChannelFuture future = bootstrap.bind(port).sync();

            // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();
            System.out.println("服务端处理结束");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            // 退出
            group.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler
            extends ChannelInitializer<SocketChannel>{
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            System.out.println("【"+ Thread.currentThread().getName()+ "】初始化channel");

            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new NettyTimeServerHandler());
        }
    }

    public static void main(String[] args){
        int port = 8080;

        new TimeServer().bind(port);

    }

}
