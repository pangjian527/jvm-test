package com.pj.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHanlder implements Runnable {

    public AsynchronousServerSocketChannel channel ;

    public CountDownLatch latch ;

    public AsyncTimeServerHanlder(int port) {
        try{
            channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(port));
            System.out.println("开始绑定监听端口...");
        }catch (IOException e ){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);

        try {
            channel.accept(this,new AcceptCompletionHandler());
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
