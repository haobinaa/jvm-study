package com.haobin.jvmstudy.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池异常处理
 */
public class ThreadException {

    static ExecutorService executorService = Executors.newFixedThreadPool(1);


    public static void ThreadPoolExceptionSubmit() {
        executorService.submit(() -> {
            int i = 1 / 0;
            System.out.println("submit 执行");
        });
    }

    public static void ThreadPoolExceptionSubmitFuture() {
        try {
            Future<String> f = executorService.submit(() -> {
                int i = 1 / 0;
                System.out.println("submit 执行");
                return "success";
            });
            f.get();
        } catch (Exception e) {
            System.out.println("submit future get exeception:" + e.getMessage());
        }
    }

    public static void ThreadPoolExceptionExecution() {

        executorService.execute(() -> {
            int i = 1 / 0;
            System.out.println("execute 执行");
        });
    }


    public static void invokeALlException() {
        List<Callable<String>> callableLists = new ArrayList<>();
        callableLists.add(() -> {
            int i = 1/0;
            System.out.println("callable 执行");
           return "success";
        });
        try {
            executorService.invokeAll(callableLists);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        } catch (Exception e) {
            System.out.println("catch exception:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
//        ThreadPoolExceptionSubmit();
//        ThreadPoolExceptionExecution();
//        ThreadPoolExceptionSubmitFuture();
        invokeALlException();
    }
}
