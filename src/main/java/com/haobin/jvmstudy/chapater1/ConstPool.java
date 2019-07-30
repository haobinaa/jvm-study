package com.haobin.jvmstudy.chapater1;

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


        String s1 = new String("1") + new String("1");
        s1.intern();
        String s2 = "11";
        System.out.println(s1 == s2);

        String s3 = new String("2") + new String("2");
        String s4 = "22";
        s3.intern();
        System.out.println(s3 == s4);


    }
}
