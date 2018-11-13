package com.javarush.task.task33.task3310.strategy;

/**
 * Created by 123 on 10.09.2017.
 */
public class OurHashMapStorageStrategy implements StorageStrategy {
   private  static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry<Long, String>[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    int hash(Long k){
        int h = 0;

        h ^= k.hashCode();

        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

   public int indexFor(int hash, int length){
       return hash & (length-1);
   }

   public Entry<Long, String> getEntry(Long key){
       if (size == 0) {
           return null;
       }

       int hash = (key == null) ? 0 : hash(key);
       for (Entry<Long, String> e = table[indexFor(hash, table.length)];
            e != null;
            e = e.next) {
           Object k;
           if (e.hash == hash &&
                   ((k = e.key) == key || (key != null && key.equals(k))))
               return e;
       }
       return null;
   }
   public void resize(int newCapacity){
       Entry<Long, String>[] oldTable = table;
       int oldCapacity = oldTable.length;
       if (oldCapacity == 1 << 30) {
           threshold = Integer.MAX_VALUE;
           return;
       }

       Entry<Long, String>[] newTable = new Entry[newCapacity];
       transfer(newTable);
       table = newTable;
       threshold = (int)Math.min(newCapacity * loadFactor, 1 << 30 + 1);
   }
    void transfer(Entry<Long, String>[] newTable) {
        int newCapacity = newTable.length;
        for (Entry<Long, String> e : table) {
            while(null != e) {
                Entry<Long, String> next = e.next;

                    e.hash = null == e.key ? 0 : hash(e.key);

                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }




   public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry<Long, String> e = table[bucketIndex];
        table[bucketIndex] = new Entry<Long, String>(hash, key, value, e);
        size++;
    }



    @Override
    public boolean containsKey(Long key) {
//int hash=hash(key);
//
//        int i=indexFor(hash,table.length);
//        for (Entry e=table[i];e!=null;e=e.next) {
//
//            if (e.hash == hash && ((e.key) == key || key.equals(e.key)))
//                return true;
//        }
//
//
//        return false;
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null)
            return false;


        for (int i = 0; i <table.length ; i++) {

            for (Entry<Long, String> e=table[i];e!=null;e=e.next){
                if(e.value.equals(value))
                    return true;
            }
            }



        return false;
    }

    @Override
    public Long getKey(String value) {
        if (value == null)
            return 0L;

        for (int i = 0; i <table.length ; i++) {
            for (Entry<Long, String> e=table[i];e!=null;e=e.next){
                if(e.value.equals(value))
                    return e.key;
            }
            }




        return null;
    }

    @Override
    public String getValue(Long key) {
//        int hash=hash(key);
//
//        int i=indexFor(hash,table.length);
//        for (Entry e=table[i];e!=null;e=e.next) {
//
//            if (e.hash == hash && ((e.key) == key || key.equals(e.key)))
//                return e.value;
//        }
//        return null;
        return null == getEntry(key) ? null : getEntry(key).getValue();
    }

    @Override
    public void put(Long key, String value) {
//        int hash;
//        if (key==null) {
//            hash = 0;
//        }else
//        hash=hash(key);
//        int i=indexFor(hash,table.length);
//        for (Entry e=table[i];e!=null;e=e.next) {
//
//            if (e.hash == hash && ((e.key) == key || key.equals(e.key)))
//               e.value=value ;
//            else {
//                addEntry(hash,key,value,i);
//            }
//        }
        addEntry(hash(key), key, value, indexFor(hash(key),table.length));




    }
}
