package com.pj.thread;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class ThreadTest1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                System.out.println("异步-->"+System.currentTimeMillis());
                System.out.println("异步任务...");
                try{
                    Thread.sleep(10000);
                }catch (Exception e){

                }

                return "完成";
            }
        });
        System.out.println("停止-->"+System.currentTimeMillis());

        Thread.sleep(1000000);
        future.complete("World");

        System.out.println(future.get());

    }


    public static void test2(){

    }

    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<String> future = executor.submit(() -> {
            System.out.println("running task");
            Thread.sleep(10000);
            return "return task";
        });

        System.out.println("异步线程正在执行...");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }finally {

            System.out.println(future.get());

            System.out.println("完成");

            executor.shutdown();
        }
    }
}
