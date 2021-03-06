package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class HashSetAdapter implements Adapter<HashSet> {
    @Override
    public HashSet read(String clazzName, ObjectReader reader) {
        reader.getReader().skip(2);// block start
        int cap = reader.getReader().readInt();
        reader.getReader().skip(4); // loadFactor
        int elemSize = reader.getReader().readInt();
        HashSet items = new HashSet(cap);
        for (int i = 0; i < elemSize; i++) {
            Object item = reader.readObject();
            items.add(item);
        }
        reader.getReader().skip(1); // block end
        return items;
    }
}
