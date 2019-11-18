package com.haobin.jvmstudy.bytecode.instrumention;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author: HaoBin
 * @create: 2019/11/18 16:09
 * @description: transform 实现, 等于 jvm 级别的aop
 **/
public class GreetingTransform implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if ("com/haobin/jvmstudy/bytecode/instrumention/Hello".equals(className)) {
            System.out.println("catch class =====> " + System.currentTimeMillis());
        }
        return null;
    }
}
