package com.l1yp.stream;

import java.util.Arrays;

/**
 * @Author Lyp
 * @Date 2020-07-02
 * @Email l1yp@qq.com
 */
public class ObjectDescriptor {
    String name;
    long serialId;
    byte flags;
    FieldDescriptor[] fields;
    ObjectDescriptor parent;

    @Override
    public String toString() {
        return "ObjectDescriptor{" +
                "name='" + name + '\'' +
                ", serialId=" + serialId +
                ", flags=" + flags +
                ", fields=" + Arrays.toString(fields) +
                '}';
    }

    public static class FieldDescriptor {
        Class<?> type;
        String typeName;
        byte typeCode;
        String name;
        Object val;

        @Override
        public String toString() {
            return "FieldDescriptor{" +
                    "type=" + type +
                    ", typeName='" + typeName + '\'' +
                    ", typeCode=" + typeCode +
                    ", name='" + name + '\'' +
                    ", val=" + val +
                    '}';
        }
    }

}
