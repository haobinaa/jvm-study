package com.haobin.jvmstudy.chapater2;

/**
 * @Author haobin
 * @Date 2019-08-11 22:06
 * @Description 嵌套类的加载测试
 **/
public class MyCat {

    public MyCat() {
        System.out.println("MyCat is loaed by:" + this.getClass().getClassLoader());
        System.out.println("from Mycat:" + MySimple.class);
    }
}

class MySimple {
    public MySimple() {
        System.out.println("MySample is loaded by:" + this.getClass().getClassLoader());
        new MyCat();
    }
}

class Main {
    public static void main(String[] args) throws Exception{
        CustomClassLoader loader = new CustomClassLoader("loader1");
        Class<?> classz = loader.loadClass("com.haobin.jvmstudy.chapater2.MySimple");
        System.out.println(classz.hashCode());
        /**
         *  1. 如果将 MyCat.class 从 classpath 删除， 则会抛出classNotFindException,因为会以加载 MySimple 的 classLoader 尝试加载 MyCat.class
         *  2. 如果将 MySimple 从 classpath 删除，放到桌面上， 则 MySimple会由loader1加载，MyCat由AppClassloader加载。此时 将抛出 ClassNotFountException
         *  因为在 MyCat 的构造函数输出了 MySimple.class， 父类加载器的命名空间是看不到子类加载器所加载的类的(这里参考类命名空间的定义)
         *  也就是说 AppClassLoader 加载的 MyCat 类， 但是却找不到 MySimple.class(是由自定义类加载器加载的，不在classpath下)
          */
        Object object = classz.newInstance();

    }
}