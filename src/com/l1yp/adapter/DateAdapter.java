package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;

import java.util.Date;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class DateAdapter implements Adapter<Date> {
    @Override
    public Date read(String clazzName, ObjectReader reader) {
        if (clazzName.equals(Date.class.getName())){
            reader.getReader().skip(1); //  block start
            byte len = reader.getReader().read();
            long millions = reader.getReader().readLong();
            reader.getReader().skip(1); //  block end
            System.out.println("millions = " + millions);
            return new Date(millions);
        }
        return null;
    }
}
