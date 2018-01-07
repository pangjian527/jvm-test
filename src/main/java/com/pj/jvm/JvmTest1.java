package com.pj.jvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

class Order{
    private String title;
    private double price;
    private int count;

    public Order(String title, double price, int count) {
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

public class JvmTest1 {

    public static void main(String[] args) throws Exception{

        while (true){
            System.out.println("测试");
            Thread.sleep(1000);
        }

    }
}
