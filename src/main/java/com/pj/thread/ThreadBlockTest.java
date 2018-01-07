package com.pj.thread;

public class ThreadBlockTest implements Runnable{

    @Override
    public void run() {
        synchronized (ThreadBlockTest.class){
            while (true){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }

            }
        }
    }

    public static void main(String[] args){
        new Thread(new ThreadBlockTest(),"Block-test-1").start();
        new Thread(new ThreadBlockTest(),"Block-test-2").start();
    }
}
