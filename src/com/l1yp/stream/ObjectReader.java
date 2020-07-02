package com.l1yp.stream;

import com.l1yp.stream.ObjectDescriptor.FieldDescriptor;
import com.l1yp.util.Packet;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.ObjectStreamField;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.io.ObjectStreamConstants.*;

/**
 * @Author Lyp
 * @Date   2020-07-02
 * @Email  l1yp@qq.com
 */
public class ObjectReader {

    private Packet reader = null;
    private List<String> references;

    public ObjectReader(byte[] bytes){
        reader = new Packet(bytes);
        reader.skip(4);// magic number
        references = new ArrayList<>();
    }

    public void readObject(){
        while (reader.remaining() > 0){
            byte type = reader.peek();
            switch (type){
                case TC_OBJECT: {
                    readOrdinaryObject();
                    break;
                }
            }
        }
    }

    private void readOrdinaryObject(){
        if (reader.read() != TC_OBJECT){
            throw new InternalError();
        }


        byte superTag;
        do {
            byte type = reader.peek();
            ObjectDescriptor descriptor = null;
            switch (type){
                case TC_CLASSDESC: {
                    descriptor = readClassDesc();
                    System.out.println("descriptor = " + descriptor);
                    break;
                }
                default:
                    throw new UnsupportedOperationException("不支持的描述类型");
            }

            reader.skip(1); // end
            superTag = reader.peek();
        }while (superTag != TC_NULL);

        reader.skip(1);

        //TODO read field value

    }

    private ObjectDescriptor readClassDesc(){
        byte tc = reader.peek();
        ObjectDescriptor descriptor;
        switch (tc) {
            case TC_CLASSDESC:
                descriptor = readNonProxyDesc();
                break;
            default:
                throw new UnsupportedOperationException(
                        String.format("invalid type code: %02X", tc));
        }
        return descriptor;

    }

    private ObjectDescriptor readNonProxyDesc(){
        if (reader.read() != TC_CLASSDESC){
            throw new InternalError();
        }
        ObjectDescriptor descriptor = new ObjectDescriptor();
        descriptor.name = reader.readUTF();
        references.add(descriptor.name);
        descriptor.serialId = reader.readLong();
        descriptor.flags = reader.read();

        int fieldNums = reader.readUShort();
        descriptor.fields = new FieldDescriptor[fieldNums];

        for (int i = 0; i < fieldNums; i++) {
            descriptor.fields[i] = new FieldDescriptor();

            byte tag = reader.read();
            descriptor.fields[i].name = reader.readUTF();
            descriptor.fields[i].typeCode = tag;
            descriptor.fields[i].type = readType(tag);
            if (tag == 'L' || tag == '['){
                tag = reader.read(); // tag
                if (tag == TC_REFERENCE){
                    int index = reader.readInt() - 8257536;
                    descriptor.fields[i].typeName = references.get(index);
                    // TODO need field Type
                }else {
                    descriptor.fields[i].typeName = reader.readUTF();
                    references.add(descriptor.fields[i].typeName);
                }
            }

        }

        computeFieldOffsets(descriptor.fields);

        return descriptor;
    }

    private Class<?> readType(byte tag){
        switch (tag) {
            case 'Z': return Boolean.TYPE;
            case 'B': return Byte.TYPE;
            case 'C': return Character.TYPE;
            case 'S': return Short.TYPE;
            case 'I': return Integer.TYPE;
            case 'J': return Long.TYPE;
            case 'F': return Float.TYPE;
            case 'D': return Double.TYPE;
            case 'L':
            case '[': return Object.class;
            default: throw new IllegalArgumentException("illegal signature");
        }
    }

    private void computeFieldOffsets(FieldDescriptor[] fields) {
        int primDataSize = 0;
        int numObjFields = 0;
        int firstObjIndex = -1;

        for (int i = 0; i < fields.length; i++) {
            FieldDescriptor f = fields[i];
            switch (f.typeCode) {
                case 'Z':
                case 'B':
                    f.offset = primDataSize++;
                    break;
                case 'C':
                case 'S':
                    f.offset = primDataSize;
                    primDataSize += 2;
                    break;

                case 'I':
                case 'F':
                    f.offset = primDataSize;
                    primDataSize += 4;
                    break;

                case 'J':
                case 'D':
                    f.offset = primDataSize;
                    primDataSize += 8;
                    break;

                case '[':
                case 'L':
                    f.offset = primDataSize++;
                    if (firstObjIndex == -1) {
                        firstObjIndex = i;
                    }
                    break;

                default:
                    throw new InternalError();
            }
        }
    }



}
