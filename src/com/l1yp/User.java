package com.l1yp;

import java.io.Serializable;

/**
 * @Author Lyp
 * @Date 2020/7/2
 * @Email l1yp@qq.com
 */
public class User extends Account implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 2047279823485445574L;

    UserDetail detail;
    String name;
    int age;
    long id;

    @Override
    public String toString() {
        return "User{" +
                "detail=" + detail +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", money=" + money +
                ", accountName='" + accountName + '\'' +
                '}';
    }
}
