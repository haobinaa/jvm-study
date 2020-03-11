package com.haobin.jvmstudy.java8;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @Author HaoBin
 * @Create 2020/3/11 14:49
 * @Description: optional 使用
 **/
public class OptionalT {


    public static void main(String[] args) {
        // 创建空的 optional
        Optional<Object> empty = Optional.empty();
        System.out.println(empty);
        // 创建非空的 optional, 如果为空则抛异常
        Optional<String> opt = Optional.of("hello world");
        System.out.println(opt);
        String name = null;
        // 创建可以为空的 optional
        Optional<String> optOrNull = Optional.ofNullable(name);
        System.out.println(optOrNull);
        // 判断值是否存在
        System.out.println(opt.isPresent());
        // 非空表达式, 跳过判空执行 lambda 表达式, 如果不采用这个方式首先要对 str 进行非空判断
        opt.ifPresent(str -> System.out.println(str.length()));
        // 设置默认值
        String nullName = null;
        String trueName = Optional.ofNullable(nullName).orElse("hello");
        System.out.println(trueName);
        String password = "123456";
        Optional<String> optPassword = Optional.ofNullable(password);
        // 过滤值, 如果不满足则返回false，否则返回对应值
        System.out.println(optPassword.filter(pwd -> pwd.length() > 6).isPresent());
        // 多条件过滤值(校验密码长度在6-10位)
        Predicate<String> len6 = pwd -> pwd.length() > 6;
        Predicate<String> len10 = pwd -> pwd.length() < 10;
        System.out.println(optPassword.filter(len6.and(len10)).isPresent());
        // 转换值， map 将原来的 optional 转换成一个新的 optional 而不影响原来的 optional 对象
        String upperCase = "HELLO";
        Optional<String> mapOpt = Optional.ofNullable(upperCase);
        System.out.println(mapOpt.map(String::toLowerCase).orElse(""));
    }
}
