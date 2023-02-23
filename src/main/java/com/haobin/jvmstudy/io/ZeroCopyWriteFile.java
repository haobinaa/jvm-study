package com.haobin.jvmstudy.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;

public class ZeroCopyWriteFile {


    public static final int _4KB = 4 * 1024;

    public static final int _GB = 1024 * 1024 * 1024;


    /**
     * 输出: 2122 ms
     * 2023-02-22T16:27:13.409
     * 2023-02-22T16:27:15.531
     *
     * @throws IOException
     */
    public static void writeOnePage() throws IOException {
        System.out.println(LocalDateTime.now());
        FileChannel fileChannel = new RandomAccessFile(new File("test.log"), "rw").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(_4KB);
        for (int i = 0; i < _4KB; i++) {
            byteBuffer.put((byte) 0);
        }
        for (int i = 0; i < _GB; i += _4KB) {
            byteBuffer.position(0);
            byteBuffer.limit(_4KB);
            fileChannel.write(byteBuffer);
        }
        System.out.println(LocalDateTime.now());
    }

    /**
     * 实测一分钟大概写 8MB 左右
     * @throws IOException
     */
    public static void writeOneBytePageCache() throws IOException {
        System.out.println(LocalDateTime.now());
        FileChannel fileChannel = new RandomAccessFile(new File("test.log"), "rw").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.put((byte) 0);
        for (int i = 0; i < _GB; i++) {
            byteBuffer.position(0);
            byteBuffer.limit(1);
            fileChannel.write(byteBuffer);
        }
        System.out.println(LocalDateTime.now());
    }

    /**
     * 输出:
     * 2023-02-22T17:15:30.044
     * 2023-02-22T17:15:31.227
     * @throws IOException
     */
    public static void mmapWrite() throws IOException {
        System.out.println(LocalDateTime.now());
        FileChannel fileChannel = new RandomAccessFile(new File("test.log"), "rw").getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, _GB);
        for (int i = 0; i < _GB; i++) {
            mappedByteBuffer.put((byte) 0);
        }
        System.out.println(LocalDateTime.now());
    }


    public static void main(String[] args) throws IOException {
//        writeOnePage();
//        writeOneBytePageCache();
        mmapWrite();
    }
}
