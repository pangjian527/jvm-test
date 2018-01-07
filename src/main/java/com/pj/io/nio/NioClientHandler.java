package com.pj.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioClientHandler implements Runnable{

    private Selector selector;
    private SocketChannel socketChannel;

    private String ip;
    private int port;
    private boolean stop = false;

    public NioClientHandler(String ip,int port) {

        this.ip = ip;
        this.port = port;

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();

            socketChannel.configureBlocking(false);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {

        try {
            doConnect();
        }catch (IOException e){

        }

        while (!stop){
            try {
                selector.select(1000);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    try{
                        handlerInput(selectionKey);
                    }catch (IOException e){
                        e.printStackTrace();

                        if(selectionKey != null){
                            selectionKey.cancel();
                            if(selectionKey.channel() != null){
                                selectionKey.channel().close();
                            }
                        }
                    }

                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void doConnect() throws IOException{

        System.out.println("发起链接...");
        if(socketChannel.connect(new InetSocketAddress(ip,port))){
            socketChannel.register(selector,SelectionKey.OP_READ);
            System.out.println("监听读操作..");
        }else{
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
            System.out.println("监听链接建立...");
        }
    }

    private void handlerInput(SelectionKey selectionKey) throws IOException {

        if(selectionKey.isValid()){
            SocketChannel sc = (SocketChannel) selectionKey.channel();

            System.out.println("执行handler...");
            if(selectionKey.isConnectable()){
                System.out.println("isConnectable...");
                if(sc.finishConnect()){
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                }else {
                    System.exit(1);
                }
            }

            if(selectionKey.isReadable()){
                System.out.println("isReadable...");
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                // 读取字节到缓冲区
                int readBytes = sc.read(byteBuffer);

                if(readBytes > 0){
                    byteBuffer.flip();

                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);

                    String body = new String(bytes,"UTF-8");
                    System.out.println("读取到的消息是：" + body);
                }else if (readBytes < 0){
                    selectionKey.cancel();
                    sc.close();
                }else{
                    System.out.println("读取不到数据...");
                }
            }
        }

    }

    private void doWrite(SocketChannel sc) throws IOException {

        byte[] bytes = "ServerTime".getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        sc.write(buffer);

        if(!buffer.hasRemaining()) {
            System.out.println("发送两次请求成功...");
        }
    }
}
