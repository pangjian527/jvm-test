package com.pj.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTimeServer {

    public static void main(String[] args){

        int port = 8080;

        if(args != null && args.length > 0){
            port = Integer.valueOf(args[0]);
        }

        ServerSocket ss = null;

        System.out.println("开始监听8080端口...");

        try{
            ss = new ServerSocket(port);
            int i =0;
            while (true){
                Socket socket = ss.accept();
                System.out.println("监听到连接事件..");
                i++;
                new Thread(new BioTimeHanlder(socket),"test-bio-"+i).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(ss != null){
                try {
                    ss.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }

    }

}
