package com.haobin.jvmstudy.jmm;

/**
 * @Author HaoBin
 * @Create 2020/1/13 17:42
 * @Description: 可见性的其他可能
 **/
public class Visiable {

    private static boolean keepRunning =true;

    /**
     * 除了 happens-before 规则之外的可见性测试
     * 1. Thread.sleep
     * 2.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        new Thread(() -> {
            while (keepRunning) {
                // doSomething
                System.out.println("测试");
            }
            System.out.println("停止循环");
        }).start();
        Thread.sleep(500);
        keepRunning = false;
        System.out.println("改变停止标志");
    }
}
