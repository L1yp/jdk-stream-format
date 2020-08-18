package com.l1yp.stream;

import java.util.Arrays;

/**
 * @Author Lyp
 * @Date 2020-07-02
 * @Email l1yp@qq.com
 */
public class ObjectDescriptor implements Cloneable {
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

    public ObjectDescriptor dup() {
        ObjectDescriptor descriptor = new ObjectDescriptor();
        descriptor.fields = new FieldDescriptor[this.fields.length];
        System.arraycopy(this.fields, 0, descriptor.fields, 0, this.fields.length);
        for (int i = 0; i < this.fields.length; i++) {
            descriptor.fields[i] = this.fields[i].dup();
        }

        descriptor.parent = this.parent != null ? this.parent.dup() : null;
        descriptor.flags = this.flags;
        descriptor.name = this.name;
        descriptor.serialId = this.serialId;

        return descriptor;
    }

    public static class FieldDescriptor implements Cloneable {
        Class<?> type;
        String typeName;
        byte typeCode;
        String name;
        Object val;

        public FieldDescriptor dup() {
            FieldDescriptor descriptor = new FieldDescriptor();
            descriptor.type = this.type;
            descriptor.typeName = this.typeName;
            descriptor.typeCode = this.typeCode;
            descriptor.name = this.name;
            descriptor.val = null;
            return descriptor;
        }

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
