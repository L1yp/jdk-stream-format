package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class LinkedListAdapter implements Adapter<LinkedList> {

    @Override
    public LinkedList<Object> read(String clazzName, ObjectReader reader) {

        reader.getReader().skip(2);
        int size = reader.getReader().readInt();
        LinkedList<Object> result = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            Object item = reader.readObject();
            result.addLast(item);
        }
        reader.getReader().skip(1);// end block

        return result;
    }
}
