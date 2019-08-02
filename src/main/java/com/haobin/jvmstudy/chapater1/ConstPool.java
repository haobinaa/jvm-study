package com.haobin.jvmstudy.chapater1;

import java.lang.reflect.Field;

/**
 * @Author haobin
 * @Date 2019-07-29 23:18
 * @Description TODO
 **/
public class ConstPool {

    public static void main(String[] args) {
//        String s1 = "Hello";
//        String s2 = "Hello";
//        String s3 = "Hel" + "lo";
//        String s4 = "Hel" + new String("lo");
//        String s5 = new String("Hello");
//        String s6 = s5.intern();
//        String s7 = "H";
//        String s8 = "ello";
//        String s9 = s7 + s8;
//
//        System.out.println(s1 == s2);  // true
//        System.out.println(s1 == s3);  // true
//        System.out.println(s1 == s4);  // false
//        System.out.println(s1 == s9);  // false
//        System.out.println(s4 == s5);  // false
//        System.out.println(s1 == s6);  // true
//        String str1 = new String("ab" + "c");


//        String s1 = new String("1") + new String("1");
//        s1.intern();
//        String s2 = "11";
//        System.out.println(s1 == s2);
//
//        String s3 = new String("2") + new String("2");
//        String s4 = "22";
//        s3.intern();
//        System.out.println(s3 == s4);


        /**
         * 通过反射来修改String 常量池中的值
         */
        String s1 = "abcd";
        String s2 = "abcd";
        String s3 = new String("abcd");
        String s4 = new String(s1);
        System.out.println("s1==s2:" + (s1 == s2));
        System.out.println("s1==s3:" + (s1 == s3));
        System.out.println("s3==s4:" + (s3 == s4));
        try {
            Field f = String.class.getDeclaredField("value");
            System.out.print("\nAccessible: " + f.isAccessible());
            f.setAccessible(true);
            System.out.println(" -> " + f.isAccessible());
            char[] v = (char[]) f.get(s1);// 获取S1的内部value数组
            System.out.print("\nvalue:");
            System.out.println(v);
            v[0] = 'x';// 修改数组
            v = new char[1];// 目测因为是get到的是引用的复制，这个引用改变不影响原引用
            v[0] = 'y';
            System.out.print("value:");
            System.out.println(v);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\ns1:" + s1 + "\ns2:" + s2 + "\ns3:" + s3 + "\ns4:" + s4);
        System.out.println("\ns1==s2:" + (s1 == s2));
        System.out.println("s1==s3:" + (s1 == s3));
        System.out.println("s3==s4:" + (s3 == s4));

    }
}
