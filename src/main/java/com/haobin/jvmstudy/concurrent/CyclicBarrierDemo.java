package com.haobin.jvmstudy.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author HaoBin
 * @Create 2020/3/14 22:43
 * @Description:
 **/
public class CyclicBarrierDemo {


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new TourGuideTask());
        Executor executor = Executors.newFixedThreadPool(3);
        //登哥最大牌，到的最晚
        executor.execute(new TravelTask(cyclicBarrier, "哈登", 5));
        executor.execute(new TravelTask(cyclicBarrier, "保罗", 3));
        executor.execute(new TravelTask(cyclicBarrier, "戈登", 1));
        // 第二轮
        executor.execute(new TravelTask(cyclicBarrier, "科比", 5));
        executor.execute(new TravelTask(cyclicBarrier, "詹姆斯", 3));
        executor.execute(new TravelTask(cyclicBarrier, "库里", 1));
    }

    static class TravelTask implements Runnable {

        private CyclicBarrier cyclicBarrier;
        private String name;
        private int arriveTime;//赶到的时间

        public TravelTask(CyclicBarrier cyclicBarrier, String name, int arriveTime) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.arriveTime = arriveTime;
        }

        @Override
        public void run() {
            try {
                //模拟达到需要花的时间
                Thread.sleep(arriveTime * 1000);
                System.out.println(name + "到达集合点");
                cyclicBarrier.await();
                System.out.println(name + "开始旅行啦～～");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    static class TourGuideTask implements Runnable {
        @Override
        public void run() {
            System.out.println("****导游分发护照签证****");
            try {
                //模拟发护照签证需要2秒
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
