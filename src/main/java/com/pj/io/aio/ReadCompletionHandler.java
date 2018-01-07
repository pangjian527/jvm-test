package com.pj.io.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{

    private AsynchronousSocketChannel channel ;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }


    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();

        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        try {
            String body = new String(bytes,"UTF-8");
            //System.out.println(body);

            String response = body.equals("ServerTime")? new Date().toString(): "fail";

            doWrite(response);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void doWrite(String response) {

        byte[] bytes = response.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();

        channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer byteBuffer) {
                if(buffer.hasRemaining()){
                    channel.write(byteBuffer,byteBuffer,this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try{
                    channel.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try{
            channel.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
