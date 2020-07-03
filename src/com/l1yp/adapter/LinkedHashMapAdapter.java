package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;
import com.l1yp.util.Bits;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * HashMap是自定义序列化,LinkedHashMap继承HashMap
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class LinkedHashMapAdapter implements Adapter<LinkedHashMap> {
    @Override
    public LinkedHashMap read(String clazzName, ObjectReader reader) {
        int i = reader.getReader().readInt();
        float loadFactor = Float.intBitsToFloat(i);
        int threshold = reader.getReader().readInt();
        reader.getReader().skip(2); // block start
        reader.getReader().skip(4); // buckets size
        int mappings = reader.getReader().readInt();
        int cap = (int)(threshold / loadFactor);

        LinkedHashMap map = new LinkedHashMap(cap, loadFactor);

        for (int j = 0; j < mappings; j++) {
            Object key = reader.readObject();
            Object val = reader.readObject();
            map.put(key, val);
        }

        reader.getReader().skip(1); // block end

        boolean accessOrder = reader.getReader().read() != 0;


        LinkedHashMap result = new LinkedHashMap(cap, loadFactor, accessOrder);
        result.putAll(map);
        return result;
    }
}
