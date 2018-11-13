package com.javarush.task.task33.task3310.strategy;

/**
 * Created by 123 on 11.09.2017.
 */
public class FileStorageStrategy implements StorageStrategy {
    private  static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    private int size;
    private FileBucket[] table =new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private long bucketSizeLimit=DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize;

    public FileStorageStrategy() {
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            table[i] = new FileBucket();
        }
    }
    private int hash(Long k) {
        long h = k;
        h ^= (h >>> 20) ^ (h >>> 12);
        return (int) (h ^ (h >>> 7) ^ (h >>> 4));
    }
    private int indexFor(int hash, int length) {
        return hash % (length - 1);
    }
    private Entry<Long, String> getEntry(Long key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        if (table[index] != null) {
            Entry<Long, String> entry = table[index].getEntry();
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    return entry;
                }
                entry = entry.next;
            }
        }
        return null;
    }
    private void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
        maxBucketSize = 0;
        for (int i = 0; i < table.length; i++) {
            long bucketSize = countBucketSize(i);
            if (bucketSize > maxBucketSize) maxBucketSize = bucketSize;
        }
    }
    private void transfer(FileBucket[] newTable) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Entry<Long, String> entry = table[i].getEntry();
            while (entry != null) {
                Entry<Long, String> next = entry.next;
                int newIndex = indexFor(entry.hash, newTable.length);
                if (newTable[newIndex] == null) {
                    entry.next = null;
                    newTable[newIndex] = new FileBucket();
                } else {
                    entry.next = newTable[newIndex].getEntry();
                }
                newTable[newIndex].putEntry(entry);
                entry = next;
            }
            table[i].remove();
        }
    }
    private void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry<Long, String> entry = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry<Long, String>(hash, key, value, entry));
        size++;
        long bucketSize = countBucketSize(bucketIndex);
        if (bucketSize > maxBucketSize) maxBucketSize = bucketSize;
        if (table[bucketIndex].getFileSize() > bucketSizeLimit) resize(2 * table.length);
    }
    private void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex] = new FileBucket();
        table[bucketIndex].putEntry(new Entry<Long, String>(hash, key, value, null));
        size++;
        long bucketSize = countBucketSize(bucketIndex);
        if (bucketSize > maxBucketSize) maxBucketSize = bucketSize;
    }
    private long countBucketSize(int bucketIndex) {
        long res = 0;
        if (table[bucketIndex] == null) return res;
        Entry<Long, String> entry = table[bucketIndex].getEntry();
        while (entry != null) {
            res++;
            entry = entry.next;
        }
        return res;
    }
    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }
    @Override
    public boolean containsValue(String value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Entry<Long, String> entry = table[i].getEntry();
            while (entry != null) {
                if (entry.value.equals(value)) return true;
                entry = entry.next;
            }
        }
        return false;
    }
    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        if (table[index] != null) {
            Entry<Long, String> entry = table[index].getEntry();
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    entry.value = value;
                    return;
                }
                entry = entry.next;
            }
            addEntry(hash, key, value, index);
        } else {
            createEntry(hash, key, value, index);
        }
    }
    @Override
    public Long getKey(String value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Entry<Long, String> entry = table[i].getEntry();
            while (entry != null) {
                if (entry.value.equals(value)) return entry.key;
                entry = entry.next;
            }
        }
        return null;
    }
    @Override
    public String getValue(Long key) {
        Entry<Long, String> entry = getEntry(key);
        if (entry != null) return entry.value;
        return null;
    }
    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }
    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }
    public long getMaxBucketSize() {
        return maxBucketSize;
    }
}

