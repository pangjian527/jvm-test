package com.pj.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioMultiplexeTimeServer implements Runnable{


    private boolean stop = false;
    private ServerSocketChannel serverSocketChannel ;
    private Selector selector;

    public NioMultiplexeTimeServer(int port) {
        try{
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

            System.out.println("开始监听端口：" + port);
        }catch (IOException e ){
            e.printStackTrace();
            System.exit(1);
        }
    }


    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop){
            //System.out.println("多路复用selector监听到事件...");
            try {
                // 设置无论是否有事件进来，每一秒钟都激活一次selector
                selector.select(1000);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    System.out.println("监听到链接...");
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    handlerInput(selectionKey);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException{

        if(key.isValid()){

            if(key.isAcceptable()){
                System.out.print("【" + Thread.currentThread().getName()+"】");
                System.out.println("：HandlerInput监听到链接...");
                // 处理新链接
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                SocketChannel socketChannel = ssc.accept();
                //设置客户端链接为非阻塞
                socketChannel.configureBlocking(false);

                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if(key.isReadable()){
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                // 读取字节到缓冲区
                int readBytes = sc.read(byteBuffer);

                if(readBytes > 0){
                    byteBuffer.flip();

                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);

                    String body = new String(bytes,"UTF-8");

                    System.out.print("【" + Thread.currentThread().getName()+"】");
                    System.out.println("读取到的消息是：" + body);
                    System.out.println("【睡眠模拟】业务处理...");
                    try{
                        Thread.sleep(1000 * 60 * 2);
                    }catch (Exception e){

                    }

                    String currentTime =
                            body.equals("ServerTime") ?
                                    new Date(System.currentTimeMillis()).toString(): "BAD ORDER";

                    doWrite(sc,currentTime);
                }else if (readBytes < 0){
                    key.cancel();
                    sc.close();
                }else{
                    System.out.print("【" + Thread.currentThread().getName()+"】");
                    System.out.println("读取不到数据...");
                }


            }
        }



    }

    private void doWrite(SocketChannel sc, String currentTime) throws IOException {

        if(sc != null && currentTime.trim().length() > 0){
            byte[] bytes = currentTime.getBytes();

            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            sc.write(buffer);
        }

    }
}
