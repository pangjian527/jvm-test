package com.pj.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    public static void main(String[] args){

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for( int i=0 ;i< 10 ;i++){

            if(i%2 == 0){
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            System.out.println(Thread.currentThread().getName() + "：睡觉");
                            Thread.sleep(1000 * 60 *5);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
            }else{
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + "：执行完成");
                    }
                });
            }

        }
    }

}
