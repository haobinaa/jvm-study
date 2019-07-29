package com.haobin.jvmstudy.chapater1;

import java.util.UUID;

/**
 * @Author haobin
 * @Date 2019-07-29 22:53
 * @Description 常量加载的本质
 **/
public class ConstLoad {
    public static void main(String[] args) {
        /**
         * 常量在编译阶段会存入调用这个常量方法所在类的常量池中
         * 本质上，调用类并没有主动使用调用常量的类，所以不会触发加载定义常量所在的类的初始化
         * 这里常量被存入了 ConstLoad 的常量池中， 其实与 ConstLoad 没有任何关系了
         */
        System.out.println(ConstClass.str);
        System.out.println("==================");
        /**
         * 如果常量的值在编译期无法确定出来(运行期才能确定的)
         *
         */
        System.out.println(ConstClass.str1);
    }
}


class ConstClass {

    public static final String str = "hello world";

    public static final String str1 = UUID.randomUUID().toString();

    static {
        System.out.println("const class static block");
    }
}