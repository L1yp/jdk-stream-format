package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;
import com.l1yp.util.Packet;

import java.util.ArrayList;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class ArrayListAdapter implements Adapter<ArrayList> {

    public ArrayListAdapter(){}

    @Override
    public ArrayList<Object> read(String clazzName, ObjectReader reader) {
        int size = reader.getReader().readInt();
        ArrayList<Object> result = new ArrayList<>(size);
        reader.getReader().skip(6);

        for (int i = 0; i < size; i++) {
            Object item = reader.readObject();
            result.add(item);
        }
        reader.getReader().skip(1);// end block

        return result;
    }
}
