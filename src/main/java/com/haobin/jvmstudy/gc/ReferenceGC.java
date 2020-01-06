package com.haobin.jvmstudy.gc;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author: HaoBin
 * @create: 2019/11/15 17:10
 * @description: 测试 不同的引用之间的GC
 *
 * 参考：
 * - [深入理解 Reference](https://www.throwable.club/2019/02/16/java-reference/#Reference%E7%9A%84%E7%AE%80%E4%BB%8B%E5
 *  * %92%8C%E5%88%86%E7%B1%BB)
 **/
public class ReferenceGC {


    /**
     * WeakHashMap 用 WeakReference 作 key， 一旦没有指向 key 的强引用， WeakHashMap 在 GC 后删除对应的 entry
     * @throws InterruptedException
     */
    public static void weakHashMap() throws InterruptedException {
        Map<Object, Object> weakHashMap = new WeakHashMap<>();
        Object key = new Object();
        Object value = new Object();
        weakHashMap.put(key, value);
        System.out.println(weakHashMap.containsValue(value));
        // weakhashmap
        key = null;
        System.gc();

        // 等待无效的entry进入 ReferenceQueue 以便下一次调用 getTable 被清理
        Thread.sleep(1000L);

        System.out.println(weakHashMap.containsValue(value));
    }

    /**
     * SoftReference 比 WeakReference 生命力更强
     * 即使引用的对象置空了，只有当内存吃紧的情况下JVM才会清除Soft的引用对象，并且会在未来重新加载该引用的对象。
     */
    public static void softReference() {
        Object originObj = new Object();
        SoftReference<Object> softReference = new SoftReference<>(originObj);

        System.out.println(softReference.get() != null);

        originObj = null;
        System.gc();
        //soft references 只有在 jvm OutOfMemory 之前才会被回收, 所以它非常适合缓存应用
        System.out.println(softReference.get() != null);
    }
}
