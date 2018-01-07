package com.pj.io.netty.chapter07;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class MessagePackEchoServer {

    public static void main(String[] args) throws Exception{

        int port = 8080;

        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        System.out.println("开始监听端口...");
        try{
            ServerBootstrap bootstrap =new ServerBootstrap();
            bootstrap.group(group,workGroup)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {

                            channel.pipeline().addLast("frameDecoder",
                                    new LengthFieldBasedFrameDecoder(65535,0,2,0,2));

                            channel.pipeline().addLast("msgpack decoder",
                                    new MsgpackDecoder());

                            channel.pipeline().addLast("frameEncoder",
                                    new LengthFieldPrepender(2));

                            channel.pipeline().addLast("msgpack encoder",
                                    new MsgpackEncoder());

                            channel.pipeline().addLast(new MessagePackEchoServerHandler());
                        }
                    });


            ChannelFuture future = bootstrap.bind(port).sync();
            // 等待主线程端口关闭
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
