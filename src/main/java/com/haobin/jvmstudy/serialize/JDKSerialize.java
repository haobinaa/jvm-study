package com.haobin.jvmstudy.serialize;

import com.haobin.jvmstudy.bean.User;

import java.io.*;

public class JDKSerialize {

    public static void fileSerialize() throws Exception  {
        User user = new User();
        user.setId(1);
        user.setName("test");

        FileOutputStream fileOutputStream = new FileOutputStream(new File("user.log"));
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutputStream);
        objectOutput.writeObject(user);

        FileInputStream fileInputStream = new FileInputStream(new File("user.log"));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        User readUser = (User) objectInputStream.readObject();
        System.out.println(readUser);
    }


    public static void main(String[] args) throws Exception {
        fileSerialize();
    }
}
