package com.haobin.jvmstudy.chapater2;

import java.io.*;

/**
 * @Author haobin
 * @Date 2019-08-04 21:10
 * @Description 自定义classLoader
 **/
public class CustomClassLoader extends ClassLoader {

    private String classLoaderName;

    private final String fileExtension = ".class";

    public CustomClassLoader(String classLoaderName) {
        super();
        this.classLoaderName = classLoaderName;
    }

    public CustomClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }


    /**
     * loadClass方法会调用本方法加载类
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }


    /**
     *  返回字节数组
     * @param name  类名
     * @return
     */
    private byte[] loadClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        try {
            this.classLoaderName = this.classLoaderName.replace("/", ",");
            is = new FileInputStream(new File(name + this.fileExtension));
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = is.read())) {
                baos.write(ch);
            }
            data = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
               is.close();
               baos.close();
           } catch (IOException io) {
               io.printStackTrace();
           }
        }
        return data;
    }

}
