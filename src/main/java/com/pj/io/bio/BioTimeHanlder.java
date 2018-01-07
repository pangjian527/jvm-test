package com.pj.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class BioTimeHanlder implements Runnable{

    private Socket socket;
    public BioTimeHanlder(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                String body = bufferedReader.readLine();

                if(body == null){
                    break;
                }
                //System.out.println(body);

                printWriter = new PrintWriter(socket.getOutputStream());
                String respone = body.equals("ServerTime")? new Date().toString() : "fail";
                printWriter.write(respone);

                System.out.println(Thread.currentThread().getName() + "完成");
            }

        }catch (IOException e){
            e.printStackTrace();
            if(bufferedReader !=null ){
                try{
                    bufferedReader.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }

            if(printWriter != null){
                printWriter.close();
            }
            if(this.socket != null){
                try{
                    this.socket.close();
                }catch (IOException e2){
                    e2.printStackTrace();
                }

            }
        }

    }
}
