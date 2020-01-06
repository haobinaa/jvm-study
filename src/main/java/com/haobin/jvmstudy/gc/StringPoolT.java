package com.haobin.jvmstudy.gc;

import java.util.Arrays;

/**
 * @Author HaoBin
 * @Create 2019/12/30 21:51
 * @Description: string pool test
 **/
public class StringPoolT {

    public static void main(String[] args) {
        char[] chars = new char[-1 >>>8];
        Arrays.fill(chars, 'a');
        String str1 = new String(chars).intern();
        String str2 = new String(chars).intern();
        System.out.println(str1 == str2);
        System.out.println(str1.length());
        System.out.println(str2.length());
    }
}
