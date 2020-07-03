package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public interface Adapter<T> {

    T read(String clazzName, ObjectReader reader);


}
