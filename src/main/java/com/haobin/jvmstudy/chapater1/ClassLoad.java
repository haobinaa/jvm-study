package com.haobin.jvmstudy.chapater1;

/**
 * @Author haobin
 * @Date 2019-07-29 22:31
 * @Description 类加载过程
 **/
public class ClassLoad {


    public static void main(String[] args) {
        /**
         * 并不会初始化子类， 对于静态字段来说，只有直接定义了静态字段的类才会被初始化
         */
        System.out.println(MyChild.str1);

        System.out.println("=================");

        /**
         * MyChild2 和 MyParent 都会被初始化， 初始化类的时候会先去初始化该类的父类
         */
        System.out.println(Mychild2.str2);
    }
}


class MyParent {
    public static String str1 = "hello";

    static {
        System.out.println("myParent static block");
    }
}

class MyChild extends MyParent {

    static {
        System.out.println("myChild static block");
    }
}
class Mychild2 extends MyParent {

    public static String str2 = "world";

    static {
        System.out.println("myChild2 static block");
    }
}