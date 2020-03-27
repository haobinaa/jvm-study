package com.haobin.jvmstudy.concurrent;

/**
 * @Author HaoBin
 * @Create 2020/3/27 16:02
 * @Description: 缓存行影响测试
 **/
public class CacheLineEffect {

    //考虑一般缓存行大小是64字节，一个 long 类型占8字节
    static  long[][] arr;

    public static void main(String[] args) {
        arr = new long[1024 * 1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }
        long sum = 0L;
        long marked = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i+=1) {
            for(int j =0; j< 8;j++){
                sum = arr[i][j];
            }
        }
        // 横着遍历，只需要读取一次，剩下七次都是缓存行
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < 8; i+=1) {
            for(int j =0; j< 1024 * 1024;j++){
                sum = arr[j][i];
            }
        }
        // 竖着遍历，每次都要重新读取
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");


        // 结论： 效率大概相差3倍
    }
}
