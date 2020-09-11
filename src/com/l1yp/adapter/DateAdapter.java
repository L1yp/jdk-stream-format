package com.l1yp.adapter;

import com.l1yp.stream.ObjectReader;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class DateAdapter implements Adapter<Date> {
    @Override
    public Date read(String clazzName, ObjectReader reader) {
        reader.getReader().skip(1); //  block start
        byte len = reader.getReader().read();
        long millions = reader.getReader().readLong();
        reader.getReader().skip(1); //  block end
        System.out.println("millions = " + millions);

        if (clazzName.equals(Date.class.getName())){
            return new Date(millions);
        }else if (clazzName.equals(Timestamp.class.getName())){
            int nanos = reader.getReader().readInt();
            Timestamp timestamp = new Timestamp(millions);
            timestamp.setNanos(nanos);
            return timestamp;
        }
        return null;
    }
}
