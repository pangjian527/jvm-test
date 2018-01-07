package com.pj.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PriorityTest {

    static volatile boolean start = true;
    static volatile boolean end = true;


    public static void main(String[] args) throws Exception{
        List<Job> jobs = new ArrayList<Job>();

        for (int i=0 ;i< 10;i++){
            int priority = i< 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;

            Job job = new Job(priority);

            Thread thread = new Thread(job,"Thread-"+i);
            thread.setPriority(priority);
            thread.start();
            jobs.add(job);
        }

        start = false;
        TimeUnit.SECONDS.sleep(10);
        end = false;

        jobs.forEach(s -> System.out.println(s.priority +"--->"+ s.count));

    }

    static class Job implements Runnable{
        private int priority;

        private int count;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {

            while (start){
                Thread.yield();
            }

            while (end){
                count++;
                Thread.yield();
            }

        }
    }


}
