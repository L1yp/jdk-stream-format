package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;

import java.util.HashMap;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class HashMapAdapter implements Adapter<HashMap> {
    @Override
    public HashMap read(String clazzName, ObjectReader reader) {
        int i = reader.getReader().readInt();
        float loadFactor = Float.intBitsToFloat(i);
        int threshold = reader.getReader().readInt();
        reader.getReader().skip(1);
        byte len = reader.getReader().read(); // block start
        reader.getReader().skip(4); // buckets size
        int mappings = reader.getReader().readInt();
        int cap = (int)(threshold / loadFactor);

        HashMap map = new HashMap(cap);

        for (int j = 0; j < mappings; j++) {
            Object key = reader.readObject();
            Object val = reader.readObject();
            map.put(key, val);
        }

        reader.getReader().skip(1);

        return map;
    }
}
