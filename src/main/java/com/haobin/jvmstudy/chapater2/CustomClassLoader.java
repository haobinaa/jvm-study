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

    private String path;

    public void setPath(String path) {
        this.path = path;
    }

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
        System.out.println("load class invoke");
        System.out.println("load class name:" + name);
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
            name = name.replace(".", "/");
            System.out.println(name);
            is = new FileInputStream(new File(this.path + name + this.fileExtension));
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

    public static void main(String[] args) throws ClassNotFoundException {
        String path = "/Users/haobin/Desktop/";
        CustomClassLoader loader1 = new CustomClassLoader("loader1");
        loader1.setPath(path);
        Class<?> classzz = loader1.loadClass("com.haobin.jvmstudy.chapater2.CL");
        System.out.println("class:" + classzz.hashCode());
    }

}
