package com.haobin.jvmstudy.chapater2;

import com.haobin.jvmstudy.chapater1.ClassLoad;


/**
 * @Author haobin
 * @Date 2019-07-30 23:26
 * @Description TODO
 **/
public class ClassLoaderTest {

    /**
     * loadClass并不是对类对主动使用，不会导致类对初始化。
     * Class.forName是反射调用，会导致类的初始化
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Class<?> clazz = cl.loadClass("com.haobin.jvmstudy.chapater2.CL");
        System.out.println(clazz);
        System.out.println("-------");
        clazz = Class.forName("com.haobin.jvmstudy.chapater2.CL");
        System.out.println(clazz);
    }
}

class CL {
    static {
        System.out.println("class CL");
    }
}


