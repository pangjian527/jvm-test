package com.pj.io.nio;

public class NioClient {

    public static void main(String[] args){

        int port = 8080;
        if(args != null && args.length >0 ){
            port = Integer.valueOf(args[0]);
        }

        /*int i =0 ;
        while (true){
            i++;*/
            try {
                NioClientHandler timeServer = new NioClientHandler("127.0.0.1", port);
                //System.out.println("模拟第"+ i +"客户请求。");
                //Thread.sleep(500);
                new Thread(timeServer,"NIO-multiplexe-time-server-001").start();
            }catch (Exception e){

            }
        //}


    }

}
