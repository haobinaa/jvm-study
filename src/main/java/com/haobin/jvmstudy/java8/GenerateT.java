package com.haobin.jvmstudy.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author HaoBin
 * @Create 2020/4/10 11:58
 * @Description: 泛型
 **/
public class GenerateT {


    public static void main(String[] args) {
        List list = null;
        ArrayList arrayList = new ArrayList();
        list = arrayList;
    }
}
