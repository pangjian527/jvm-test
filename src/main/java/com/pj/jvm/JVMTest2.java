package com.pj.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class JVMTest2 {
    private static int apple = 10;
    private int orange = 10;
    private String test = "abc";

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        Field appleField = JVMTest2.class.getDeclaredField("test");
        System.out.println("Location of test: " + unsafe.staticFieldOffset(appleField));

        Field orangeField = JVMTest2.class.getDeclaredField("orange");
        System.out.println("Location of Orange: " + unsafe.objectFieldOffset(orangeField));
    }

    private static Unsafe getUnsafeInstance() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }

}
