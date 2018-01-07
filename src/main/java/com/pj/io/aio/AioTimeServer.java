package com.pj.io.aio;

public class AioTimeServer {

    public static void main(String[] args) {

        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }

        AsyncTimeServerHanlder handler = new AsyncTimeServerHanlder(port);
        new Thread(handler, "async-time-server-aio-0001").start();
    }

}
