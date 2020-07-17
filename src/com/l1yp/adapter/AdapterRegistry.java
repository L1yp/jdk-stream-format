package com.l1yp.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Author Lyp
 * @Date 2020-07-03
 * @Email l1yp@qq.com
 */
public class AdapterRegistry {



    static Map<String, Adapter<?>> stringAdapterMap = new HashMap<>();
    static Map<Class<?>, Adapter<?>> clazzAdapterMap = new HashMap<>();

    static {
        register(ArrayList.class, new ArrayListAdapter());
        register(LinkedList.class, new LinkedListAdapter());
        register(HashMap.class, new HashMapAdapter());
        register(LinkedHashMap.class, new LinkedHashMapAdapter());
        register(HashSet.class, new HashSetAdapter());
        register(LinkedHashSet.class, new LinkedHashSetAdapter());
        NumberAdapter numberAdapter = new NumberAdapter();
        register(Byte.class, numberAdapter);
        register(Short.class, numberAdapter);
        register(Integer.class, numberAdapter);
        register(Long.class, numberAdapter);
        register(Float.class, numberAdapter);
        register(Double.class, numberAdapter);
        register(Boolean.class, numberAdapter);
        register(Character.class, numberAdapter);
        register(Date.class, new DateAdapter());
    }

    public static Adapter<?> get(String typeName){
        return stringAdapterMap.get(typeName);
    }

    public static boolean contains(String typeName){
        return stringAdapterMap.containsKey(typeName);
    }

    public static void register(String typeName, Adapter<?> adapter){
        stringAdapterMap.put(typeName, adapter);
    }

    public static void register(Class<?> type, Adapter<?> adapter){
        stringAdapterMap.put(type.getName(), adapter);
        clazzAdapterMap.put(type, adapter);
    }

}
