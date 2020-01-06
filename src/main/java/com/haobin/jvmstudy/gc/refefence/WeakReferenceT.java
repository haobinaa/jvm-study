package com.haobin.jvmstudy.gc.refefence;

import java.lang.ref.WeakReference;

/**
 * @Author HaoBin
 * @Create 2020/1/6 10:29
 * @Description: 弱引用测试
 **/
public class WeakReferenceT {

    static class WeakEntry extends WeakReference<Object> {
        Object value;
        public WeakEntry(Object referent) {
            super(referent);
        }
    }

    /**
     * -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
     * 若只有 WeakReference 指向的对象，GC发生时就会被回收
     */
    public static void main(String[] args) {
        Object obj = new Object();
        Object value = new Object();
        WeakEntry entry = new WeakEntry(obj);
        //entry.value = value;
        System.out.println(obj == entry.get());
        obj = null;
        System.gc();
        // 触发 gc 后，仅仅只被 weakReference 指向的对象会被回收，比如 obj 对象
        System.out.println(entry.get() != null);
        System.out.println(entry != null);
    }

}
