package com.haobin.jvmstudy.concurrent;

import com.haobin.jvmstudy.bytecode.instrumention.Main;
import io.netty.util.concurrent.CompleteFuture;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * @Author HaoBin
 * @Create 2020/4/9 15:01
 * @Description: jdk 异步编程 completableFuture 使用
 **/
public class CompletableFutureT {


    public static void main(String[] args) throws Exception {
        parallelExecute();
    }

    /**
     * 普通使用 异步调用
     */
    public static void simpleUsage() throws Exception {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> fetchPrice());
        // 正常执行
        cf.thenAccept((result) -> {
            System.out.println("get price:" + result);
        });
        // 异常执行
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        System.out.println("主线程执行。。");
        new CountDownLatch(1).await();
    }
    static Double fetchPrice() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 5 + Math.random() * 20;
    }

    /**
     * 串行执行
     */
    public static void serialExecute() throws Exception {
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> queryCode());
        // 串行的执行下一个任务, 参数是上一个任务的结果
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
            System.out.println("code: " + code);
            return fetchPrice();
        });
        cfFetch.thenAccept((result) -> {
            System.out.println("fetch result:" + result);
        });
        new CountDownLatch(1).await();
    }

    static String queryCode() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        return "code001";
    }

    /**
     * 并行执行
     */
    public static void parallelExecute() throws Exception {
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });
        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });
        // 用anyOf合并为一个新的CompletableFuture: anyOf 只要其中一个成功即可
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        new CountDownLatch(1).await();
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }

}
