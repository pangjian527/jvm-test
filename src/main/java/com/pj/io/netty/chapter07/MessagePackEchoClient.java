package com.pj.io.netty.chapter07;

import com.pj.io.netty.chapter07.entity.UserInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.msgpack.MessagePack;

import java.util.Arrays;
import java.util.List;

public class MessagePackEchoClient {

    public static void main(String[] args) throws Exception{

        int port = 8080;
        String host = "127.0.0.1";

        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
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

                            channel.pipeline().addLast(new MessagePackEchoClientHandler());
                        }
                    });

            // 同步绑定端口
            ChannelFuture future = bootstrap.connect(host, port).sync();

            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

}
