package com.jvm.agent;

import java.lang.instrument.Instrumentation;

/**
 * @Author HaoBin
 * @Create 2020/3/18 14:19
 * @Description: 简单 Java agent
 **/
public class SimpleAgent {

    /**
     * jvm 参数形式启动，运行此方法
     *
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
    }

    /**
     * 动态 attach 方式启动，运行此方法
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain");
    }
}
