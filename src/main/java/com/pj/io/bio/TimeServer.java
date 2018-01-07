package com.pj.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    public static void main(String[] args){

        int port = 8080;

        if(args != null && args.length > 0){
            port = Integer.valueOf(args[0]);
        }

        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            System.out.println("开始监听8080端口...");
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("接受到请求...");
                new Thread(new TimeServerHandler(socket)).start();
            }
        }catch (IOException e ){
            e.printStackTrace();
        }finally {
            try{
                if(serverSocket != null ){
                    serverSocket.close();
                }
            }catch (IOException e){
            }
        }
    }

}
