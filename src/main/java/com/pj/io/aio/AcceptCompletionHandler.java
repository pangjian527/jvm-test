package com.pj.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHanlder>{

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHanlder attachment) {

        System.out.println(Thread.currentThread().getName());

        attachment.channel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer,buffer,new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHanlder attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
