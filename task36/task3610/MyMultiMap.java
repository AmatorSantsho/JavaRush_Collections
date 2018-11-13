package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int totalSize = 0;
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            totalSize += entry.getValue().size();
        }
        return totalSize;
    }

    @Override
    public V put(K key, V value) {
        List<V> vs = map.get(key);
        if (vs != null) {
            if (vs.size() >= repeatCount) {
                vs.remove(0);
            }
            vs.add(value);
            return vs.size() > 1 ? vs.get(vs.size() - 2) : null;
        } else {
            List<V> newVal = new ArrayList<>();
            newVal.add(value);
            map.put(key, newVal);
            return null;
        }

        //напишите тут ваш код
    }

    @Override
    public V remove(Object key) {
        List<V> values = map.get(key);
        if (values == null)
            return null;

        V storeValue = values.get(0);
        values.remove(0);

        if (values.size() == 0)
            map.remove(key);

        return storeValue;
    }
    @Override
    public Set<K> keySet() {
        Set<K> ks = map.keySet();
        return (ks != null ? ks : null);
        //напишите тут ваш код

    }

    @Override
    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (List<V> value : map.values()) {
            values.addAll(value);
        }
        return values;
    }

    @Override
    public boolean containsKey(Object key) {
        if (map.containsKey(key))
            return true;
        else
            return false;


        //напишите тут ваш код
    }

    @Override
    public boolean containsValue(Object value) {

        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            if (entry.getValue().contains(value)) {
                return true;
            }
        }
        return false;


        //напишите тут ваш код
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}