package com.pj.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable{

    private Socket socket;
    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }
        try{
            InputStreamReader reader = new InputStreamReader(this.socket.getInputStream());
            in = new BufferedReader(reader);

            out = new PrintWriter(this.socket.getOutputStream(),true);

            String body = null;
            while (true){
                body = in.readLine();
                if(body == null){
                    break;
                }

                System.out.println(Thread.currentThread().getName() +"-->body:" + body);
                String currentTime =
                        body.equals("ServerTime") ?
                                new Date(System.currentTimeMillis()).toString(): "BAD ORDER";

                out.println(currentTime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in != null){
                try{
                    in.close();
                }catch (IOException e){

                }
            }
            if(out != null){
                out.close();
            }
            if(this.socket != null){
                try{
                    this.socket.close();
                }catch (IOException e){
                }
            }
        }
    }
}