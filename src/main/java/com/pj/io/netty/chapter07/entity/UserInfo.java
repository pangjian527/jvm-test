package com.pj.io.netty.chapter07.entity;

import org.msgpack.annotation.Message;

import java.util.List;

@Message
public class UserInfo {


    public UserInfo() {
    }

    public UserInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;

    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
