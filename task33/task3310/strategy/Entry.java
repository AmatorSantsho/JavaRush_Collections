package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;

/**
 * Created by 123 on 09.09.2017.
 */
public class Entry<L, S> implements Serializable {
    Long key;
    String value;
    Entry<L, S> next;
    int hash;

    public Entry(int hash,Long key, String value, Entry<L, S> next) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry<L, S> entry = (Entry<L, S>) o;

        if (key != null ? !key.equals(entry.key) : entry.key != null) return false;
        return !(value != null ? !value.equals(entry.value) : entry.value != null);

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return key+"="+value;
    }
}
