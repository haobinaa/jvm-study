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
//        System.out.println(ConstClass.str);
//        System.out.println("==================");
        /**
         * 如果常量的值在编译期无法确定出来(运行期才能确定的)
         *
         */
//        System.out.println(ConstClass.str1);

        /**
         * 对于数组类型来说，是jvm在运行期动态生成的，表示为：
         * [Lcom.haobin.jvmstudy.chapater1.ConstClass
         * 动态生成的类型，父类型是Object
         * 对于数组来说， java 将数组的组成元素看作 Component 实际上是降低了一个维度
         */
        ConstClass[] constClasses = new ConstClass[1];
        constClasses[0].put();
        System.out.println(constClasses.getClass().getSuperclass());
    }
}


class ConstClass {

    public static final String str = "hello world";

    public static final String str1 = UUID.randomUUID().toString();

    static {
        System.out.println("const class static block");
    }

    public void put() {
        System.out.println("put");
    }
}