package com.haobin.jvmstudy.io;

import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;

public class DirectByteBufferGC {


    public static void systemGC() throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        System.in.read();
        buffer = null;
        // 触发 GC
        System.gc();
        new CountDownLatch(1).await();
    }

    public static void cleanerGC() throws InterruptedException, IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        System.in.read();
        // 通过 cleaner 来回收
        ((DirectBuffer) buffer).cleaner().clean();
        new CountDownLatch(1).await();
    }

    public static void main(String[] args) throws IOException {
        // 开启 mmap
        FileChannel fileChannel = new RandomAccessFile(new File("test.log"), "rw").getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());

        // 写
        byte[] data = new byte[4];
        int position = 8;
        // 从 mmap 当前指针写入 4b 数据
        mappedByteBuffer.put(data);
        // 指定 position 位置写入 4b 数据
        MappedByteBuffer subMmap = (MappedByteBuffer) mappedByteBuffer.slice();
        subMmap.position(position);
        subMmap.put(data);

        // 读
        byte[] readData = new byte[4];
        // 从当前指针读 4b
        mappedByteBuffer.get(data);
        // 从指定位置读 4b
        subMmap.position(position);
        subMmap.get(data);
    }
}
