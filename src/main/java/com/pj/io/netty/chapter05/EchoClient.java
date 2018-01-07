package com.pj.io.netty.chapter05;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {

    public static void main(String[] args){
        int port = 8080;
        String host = "127.0.0.1";
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(group)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {

                            ByteBuf  byteBuf = Unpooled.copiedBuffer("$_".getBytes());
                            channel.pipeline().addLast(
                                    new DelimiterBasedFrameDecoder(1024,byteBuf));

                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();

            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            group.shutdownGracefully();
        }

    }
}
