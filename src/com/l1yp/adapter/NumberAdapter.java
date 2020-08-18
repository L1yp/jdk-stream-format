package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class NumberAdapter implements Adapter<Object> {
    @Override
    public Object read(String clazzName, ObjectReader reader) {

        if (clazzName.equals(Byte.class.getName())) {
            return reader.getReader().read();
        }else if (clazzName.equals(Short.class.getName())){
            return reader.getReader().readShort();
        }else if (clazzName.equals(Integer.class.getName())){
            return reader.getReader().readInt();
        }else if (clazzName.equals(Long.class.getName())){
            return reader.getReader().readLong();
        }else if (clazzName.equals(Float.class.getName())){
            return reader.getReader().readFloat();
        }else if (clazzName.equals(Double.class.getName())){
            return reader.getReader().readDouble();
        }else if (clazzName.equals(Boolean.class.getName())){
            return reader.getReader().readBoolean();
        }else if (clazzName.equals(Character.class.getName())){
            return reader.getReader().readChar();
        }
        return null;
    }
}
