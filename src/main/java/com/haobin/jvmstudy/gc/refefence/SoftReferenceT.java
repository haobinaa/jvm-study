package com.haobin.jvmstudy.gc.refefence;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Arrays;

/**
 * @Author HaoBin
 * @Create 2020/1/6 11:54
 * @Description: soft reference 测试
 **/
public class SoftReferenceT {

    private static final int _1MB = 1024 * 1024;

    /**
     * 若只被 SoftReference 指向的对象，当内存不够的时候， GC会回收
     * -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseParNewGC -verbose:gc -XX:+PrintGCDetails
     *
     * 下面例子在内存分配不够的时候就会触发gc，回收 SoftReference 对象
     * @param args
     */
    public static void main(String[] args) throws Exception{
        SoftReference<byte[]> sr1 = new SoftReference<byte[]>(new byte[4 * _1MB]);
        SoftReference<byte[]> sr2 = new SoftReference<byte[]>(new byte[4 * _1MB]);
        SoftReference<byte[]> sr3 = new SoftReference<byte[]>(new byte[4 * _1MB]);
        SoftReference<byte[]> sr4 = new SoftReference<byte[]>(new byte[4 * _1MB]);
        System.out.println(sr1.get());
        System.out.println(sr2.get());
        System.out.println(sr3.get());
        System.out.println(sr4.get());
    }
}
