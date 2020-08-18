package com.l1yp.stream;

import com.l1yp.adapter.Adapter;
import com.l1yp.adapter.AdapterRegistry;
import com.l1yp.stream.ObjectDescriptor.FieldDescriptor;
import com.l1yp.util.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static java.io.ObjectStreamConstants.TC_BLOCKDATA;
import static java.io.ObjectStreamConstants.TC_BLOCKDATALONG;
import static java.io.ObjectStreamConstants.TC_CLASSDESC;
import static java.io.ObjectStreamConstants.TC_LONGSTRING;
import static java.io.ObjectStreamConstants.TC_NULL;
import static java.io.ObjectStreamConstants.TC_OBJECT;
import static java.io.ObjectStreamConstants.TC_REFERENCE;
import static java.io.ObjectStreamConstants.TC_STRING;

/**
 * @Author Lyp
 * @Date 2020-07-02
 * @Email l1yp@qq.com
 */
public class ObjectReader {

    private Packet reader = null;

    public Packet getReader() {
        return reader;
    }

    /**
     * readClass // TODO
     * readProxyDesc // TODO
     * readArray //TODO
     * readEnum // TODO
     * readNonProxyDesc
     * readString from readObject / readTypeString / readEnum 不包含字段名,仅缓存 字符串实例, 引用类型的签名字符串, 枚举
     * readObject
     */
    private List<Object> references;
    private static final Object unsharedMarker = new Object();


    public ObjectReader(byte[] bytes) {
        reader = new Packet(bytes);
        reader.skip(4);// magic number
        references = new ArrayList<>();
    }

    public Object readObject() {
        // TODO readString readLongString readNestedObject
        byte type = reader.peek();
        switch (type) {
            case TC_OBJECT:
                return readOrdinaryObject();
            case TC_STRING:
                case TC_LONGSTRING:
                return readString(false);
            case TC_NULL: {
                reader.skip(1);
                return null;
            }
            case TC_REFERENCE: {
                reader.skip(1);
                int index = reader.readInt() - 8257536;
                return references.get(index);
            }
            default:
                return null;
        }
    }

    private String readString(boolean unshared) {
        String str;
        byte tc = reader.read();
        switch (tc) {
            case TC_STRING:
                str = reader.readUTF();
                break;

            case TC_LONGSTRING:
                str = reader.readLongUTF();
                break;

            default:
                throw new IllegalArgumentException(String.format("invalid type code: %02X", tc));
        }
        references.add(unshared ? unsharedMarker : str);
        return str;

    }

    private Object readOrdinaryObject() {
        if (reader.read() != TC_OBJECT) {
            throw new InternalError();
        }


        List<ObjectDescriptor> nestedObj = new ArrayList<>();
        byte superTag = 0;
        ObjectDescriptor root = null;
        ObjectDescriptor last = null;
        outer:
        do {
            byte type = reader.peek();
            ObjectDescriptor descriptor = null;

            switch (type) {
                case TC_CLASSDESC: {
                    descriptor = readClassDesc();
                    if (root == null){
                        root = descriptor;
                    }
                    if (last != null){
                        last.parent = descriptor;
                    }

                    nestedObj.add(descriptor);
                    System.out.println("descriptor = " + descriptor);
                    last = descriptor;
                    break;
                }
                case TC_REFERENCE: {
                    reader.skip(1);
                    int index = reader.readInt() - 8257536;
                    descriptor = (ObjectDescriptor) references.get(index);
                    if (root == null){
                        root = descriptor;
                    }
                    if (last != null){
                        last.parent = descriptor;
                    }
                    nestedObj.add(descriptor);
                    System.out.println("reference = " + descriptor);
                    last = descriptor;

                    break outer; // TODO: 好像不需要直接跳出
                }
                default:
                    throw new UnsupportedOperationException("不支持的描述类型: " + type);
            }

            reader.skip(1); // end
            superTag = reader.peek();
        } while (superTag != TC_NULL);

        if (superTag == TC_NULL) {
            reader.skip(1);
        }

        references.add(root);

        String key;
        Object val;
        String clazzName = nestedObj.get(0).name;
        if (AdapterRegistry.contains(clazzName)) {
            int size = references.size();
            Adapter<?> adapter = AdapterRegistry.get(clazzName);
            Object result = adapter.read(clazzName, this);
            references.set(size - 1, result);
            return result;
        }

        for (int i = nestedObj.size() - 1; i >= 0; i--) {
            ObjectDescriptor item = nestedObj.get(i);

            for (FieldDescriptor field : item.fields) {
                key = field.name;
                if (field.type != null && field.type != Object.class) {
                    switch (field.typeCode) {
                        case 'Z': val = reader.readBoolean(); break;
                        case 'B': val = reader.read(); break;
                        case 'C': val = reader.readChar(); break;
                        case 'S': val = reader.readShort(); break;
                        case 'I': val = reader.readInt(); break;
                        case 'F': val = reader.readFloat(); break;
                        case 'J': val = reader.readLong(); break;
                        case 'D': val = reader.readDouble(); break;
                        default : throw new IllegalArgumentException("illegal signature");
                    }
                } else {
                    val = readObject();
                }
                field.val = val;
                System.out.println("name = " + key + ", val = " + val);
            }
        }


        return root;
    }


    private ObjectDescriptor readClassDesc() {
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

    private ObjectDescriptor readNonProxyDesc() {
        if (reader.read() != TC_CLASSDESC) {
            throw new InternalError();
        }
        ObjectDescriptor descriptor = new ObjectDescriptor();
        descriptor.name = reader.readUTF();
        references.add(descriptor); // 不缓存字段名

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
            if (tag == 'L' || tag == '[') {
                tag = reader.read(); // tag
                if (tag == TC_REFERENCE) {
                    int index = reader.readInt() - 8257536;
                    descriptor.fields[i].typeName = (String) references.get(index);
                    // TODO need field Type
                } else {
                    descriptor.fields[i].typeName = reader.readUTF();
                    references.add(descriptor.fields[i].typeName);
                }
            }

        }

        return descriptor;
    }

    private Class<?> readType(byte tag) {
        switch (tag) {
            case 'Z':
                return Boolean.TYPE;
            case 'B':
                return Byte.TYPE;
            case 'C':
                return Character.TYPE;
            case 'S':
                return Short.TYPE;
            case 'I':
                return Integer.TYPE;
            case 'J':
                return Long.TYPE;
            case 'F':
                return Float.TYPE;
            case 'D':
                return Double.TYPE;
            case 'L':
            case '[':
                return Object.class;
            default:
                throw new IllegalArgumentException("illegal signature");
        }
    }


}
