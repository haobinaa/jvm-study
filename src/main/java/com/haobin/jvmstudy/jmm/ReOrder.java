package com.haobin.jvmstudy.jmm;

import java.util.concurrent.CountDownLatch;

/**
 * @Author HaoBin
 * @Create 2019/12/27 16:25
 * @Description: 内存重排序测试
 **/
public class ReOrder {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;


    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(1);

            Thread one = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                }
                a = 1;
                x = b;
            });

            Thread other = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                }
                b = 1;
                y = a;
            });
            one.start();
            other.start();
            // 开始赋值
            latch.countDown();
            // 保证在 main 之前执行
            one.join();
            other.join();

            String result = "第" + i + "次 (" + x + "," + y + "）";
            if (x == 0 && y == 0) {
                // 如果发生重排序才又可能走到这里， 即对x,y的赋值先于a,b的赋值(否则必为1)
                System.err.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }

}
