package com.pj.io.bio;

import java.io.*;
import java.net.Socket;

public class TimeClient {

    public static void main(String[] args){

        int threadCount = 0;

        while (true){
            threadCount ++;

            System.out.println("发送请求数量：" + threadCount);
            Socket socket = null;
            BufferedReader reader = null;
            PrintWriter writer = null;
            try{
                Thread.sleep(500);
                socket = new Socket("127.0.0.1",8080);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                writer = new PrintWriter(socket.getOutputStream(),true);
                writer.println("ServerTime");

                String resp = reader.readLine();

                System.out.println("客户端收到消息：" + resp);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(writer != null){
                    writer.close();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){

                    }
                }
                if(socket != null){
                    try {
                        socket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
