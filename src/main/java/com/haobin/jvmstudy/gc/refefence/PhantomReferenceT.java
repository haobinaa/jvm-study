package com.haobin.jvmstudy.gc.refefence;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author HaoBin
 * @Create 2020/3/15 17:35
 * @Description: 虚引用测试
 **/
public class PhantomReferenceT {

    public static void main(String[] args) {
        ReferenceQueue queue = new ReferenceQueue();
        List<byte[]> bytes = new ArrayList<>();
        PhantomReference<String> reference = new PhantomReference<String>("hello",queue);
        new Thread(() -> {
            for (int i = 0; i < 100;i++ ) {
                bytes.add(new byte[1024 * 1024]);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference poll = queue.poll();
                if (poll != null) {
                    System.out.println("虚引用被回收了：" + poll);
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        scanner.hasNext();
    }

}
