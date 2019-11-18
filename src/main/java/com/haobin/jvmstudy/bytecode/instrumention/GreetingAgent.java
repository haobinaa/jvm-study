package com.haobin.jvmstudy.bytecode.instrumention;

import java.lang.instrument.Instrumentation;

/**
 * @author: HaoBin
 * @create: 2019/11/18 16:12
 * @description: 装载 transform 的容器
 **/
public class GreetingAgent {

    // 名字和参数固定
    public static void premain(String options, Instrumentation ins) {
        if (options != null) {
            System.out.printf("I've been called with options: \"%s\"\n", options);
        }
        else {
            System.out.println("I've been called with no options.");
        }
        //装载 transform
        ins.addTransformer(new GreetingTransform());
    }
}
