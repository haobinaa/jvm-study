package com.haobin.jvmstudy.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FutureListener;
import java.awt.SystemTray;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @Author HaoBin
 * @Create 2020/4/9 11:37
 * @Description: future 和 promise 模式
 **/
public class FutureAndPromise {


    public static void main(String[] args) throws Exception {
        guavaFutureCall();
    }


    /**
     * guava 提供的异步回调方式
     */
    public static void guavaFutureCall() {
        long l = System.currentTimeMillis();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行操作中....");
                timeConsumingOperation();
                return 100;
            }
        });
        Futures.addCallback(future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer integer) {
                System.out.println("计算结果:"+ integer);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("异步处理失败：" + throwable);
            }
        }, MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor()));
        System.out.println("主线程运算耗时:" + (System.currentTimeMillis()-l));
    }

    /**
     * netty future 操作
     * 主线程并不会被阻塞， future 结果异步的通过hook获取
     */
    public static void nettyFutureCall() throws Exception {
        long l = System.currentTimeMillis();
        EventExecutorGroup group = new DefaultEventExecutor();
        io.netty.util.concurrent.Future<Integer> future = group.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行操作中。。。");
                timeConsumingOperation();
                return 100;
            }
        });
        future.addListener(new FutureListener<Object>(){
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<Object> objectFuture) throws Exception {
                System.out.println("计算结果:"+ future.get());
            }
        });
        System.out.println("主线程运算耗时:"+ (System.currentTimeMillis() - l));
        new CountDownLatch(1).await();
    }

    /**
     * 异步操作耗时
     */
    public static void futureCall() throws Exception {
        long l = System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行操作中。。。");
                timeConsumingOperation();
                return 100;
            }
        });
        System.out.println("计算结果是:" + future.get());
        System.out.println("主线程运行耗时:" + (System.currentTimeMillis()-l));
    }

    /**
     * 同步操作耗时
     */
    public static void syncCall() {
        long l = System.currentTimeMillis();
        int i = syncCalculate();
        System.out.println("计算结果:" + i);
        System.out.println("主线程运行耗时:" + (System.currentTimeMillis()-l));
    }

    static int syncCalculate() {
        System.out.println("执行操作中。。。。");
        timeConsumingOperation();
        return 100;
    }

    static void timeConsumingOperation() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
