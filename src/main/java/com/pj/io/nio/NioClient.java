package com.pj.io.nio;

public class NioClient {

    public static void main(String[] args){

        int port = 8080;
        if(args != null && args.length >0 ){
            port = Integer.valueOf(args[0]);
        }

        NioClientHandler timeServer = new NioClientHandler("127.0.0.1", port);
        new Thread(timeServer,"NIO-multiplexe-time-server-001").start();
    }

}
