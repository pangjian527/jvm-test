package com.pj.io.netty.chapter07;

import com.pj.io.netty.chapter07.entity.UserInfo;
import org.msgpack.MessagePack;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;

import java.util.Arrays;
import java.util.List;

public class MessagePackTest {

    public static void main(String[] args) throws Exception{

        MessagePack pack = new MessagePack();

        List<String> items = Arrays.asList("item1", "item2");

        UserInfo info = new UserInfo(123,"呵呵");
        byte[] write = pack.write(info);

        UserInfo read = pack.read(write,UserInfo.class);

        System.out.println(read.getName());
    }
}
