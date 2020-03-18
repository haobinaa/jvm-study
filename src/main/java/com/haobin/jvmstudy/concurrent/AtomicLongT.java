package com.haobin.jvmstudy.concurrent;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author HaoBin
 * @Create 2020/3/13 17:34
 * @Description: atomicLong/LongAdder
 **/
public class AtomicLongT {


    static AtomicLong atomicLong = new AtomicLong(1);



    public static void main(String[] args) {
        System.out.println(atomicLong.getAndIncrement());
        System.out.println(atomicLong.get());
    }
}
