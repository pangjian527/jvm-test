package com.pj.io.nio;

public class NioTimeServer {

    public static void main(String[] args){

        int port = 8080;
        if(args != null && args.length >0 ){
            port = Integer.valueOf(args[0]);
        }

        NioMultiplexeTimeServer timeServer = new NioMultiplexeTimeServer(port);
        new Thread(timeServer,"NIO-multiplexe-time-server-001").start();
    }

}
