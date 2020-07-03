package com.l1yp;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class UserDetail implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = -8095992507160725455L;
    String email;
    String phone;
    boolean sex;
    List<Account> contacts;

    Map<String, Object> map;

    @Override
    public String toString() {
        return "UserDetail{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", contacts=" + contacts +
                '}';
    }
}
