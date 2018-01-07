package com.pj.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {

    static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " threadLocal is gc;");
        }
    };

    static CountDownLatch countDownLatch = new CountDownLatch(10000);

    public static class DateParse implements Runnable{
        private int i;

        public DateParse(int i){
            this.i = i;
        }

        @Override
        public void run() {

            try{
                if(threadLocal.get() == null){
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + " DateParse is gc");
                        }
                    });

                    System.out.println(Thread.currentThread().getId() + ": create SimpleDateFormat");
                }

                threadLocal.get().parse("2017-12-25 20:15:15");
            }catch (ParseException e){
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i =0 ;i< 10000;i++){
            executorService.execute(new DateParse(i));
        }

        countDownLatch.await();
        System.out.println("mission complete");
        threadLocal = null;
        System.gc();

        System.out.println("first gc complete");
        threadLocal = new ThreadLocal<>();
        countDownLatch = new CountDownLatch(10000);

        for(int i =10000 ;i< 20000;i++){
            executorService.execute(new DateParse(i));
        }

        threadLocal = null;
        System.gc();
        System.out.println("second gc complete");

    }


}
