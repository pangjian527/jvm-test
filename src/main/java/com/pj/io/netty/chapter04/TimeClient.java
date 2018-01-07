package com.pj.io.netty.chapter04;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TimeClient {

    public static void main(String[] args) throws Exception{

        new TimeClient().connect("127.0.0.1", 8080);
    }

    public void connect(String host , int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        System.out.println("【"+ Thread.currentThread().getName() +"】开始请求链接..");

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            ChannelFuture connect = bootstrap.connect(host, port);

            connect.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    private class TimeClientHandler extends ChannelHandlerAdapter{

        private byte[] bytes ;
        private int countor;

        public TimeClientHandler() {
            System.out.println("【"+ Thread.currentThread().getName() +"】开始发送请求消息..");
            bytes = ("ServerTime" + System.getProperty("line.separator")).getBytes();
        }

        /**
         * 发送请求消息
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            ByteBuf message = null;
            for (int i=0; i< 100 ;i++){
                message = Unpooled.buffer(bytes.length);
                message.writeBytes(bytes);
                ctx.writeAndFlush(message);
            }

        }

        /**
         * 读取到消息
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
           /*
            增加加码器解决粘包问题，并且不用不行也要对消息进行解码，直接转成字符串即可

            ByteBuf byteBuf = (ByteBuf) msg;

            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            String response = new String(bytes,"UTF-8");*/

           String response = (String) msg;

            System.out.println("【" + Thread.currentThread().getName() +"】客户端收到返回消息："
                    +response + "; countor:" + ++countor);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }

}
