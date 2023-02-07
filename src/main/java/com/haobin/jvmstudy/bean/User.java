package com.haobin.jvmstudy.bean;

import java.io.Serializable;

public class User implements Serializable {

    // 如果不设定 serialVersionUID jvm 会根据类结构信息生成一个， 发序列化后结构发生了变更可能会序列化失败
    private static final long serialVersionUID = -5148906332276428088L;
    private Integer id;
    private String name;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
