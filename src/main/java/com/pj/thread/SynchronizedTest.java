package com.pj.thread;

public class SynchronizedTest {


    public static void main(String[] args){
        synchronized (SynchronizedTest.class){
            int a = 0;
        }
    }

    static synchronized void m(){
        int b = 1;
    }
}
