package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Created by 123 on 10.08.2017.
 */
public class AmigoSet  <E> extends AbstractSet<E> implements Serializable,Cloneable,Set<E> {

    private  static final Object PRESENT=new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet() {

        this.map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<E,Object>(Math.max(16,(int)(collection.size()/.75f+1)));
this.addAll(collection);
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone()  {
        try {
            AmigoSet copy = (AmigoSet) super.clone();
            copy.map= (HashMap) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean add(E e) {
        return null == map.put(e, PRESENT);
    }

    @Override
    public Iterator iterator() {
      Set set= map.keySet();


        return set.iterator();
    }

    @Override
    public int size() {

        return map.size();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
objectOutputStream.defaultWriteObject();
//        for (E e:map.keySet()){
//            objectOutputStream.writeObject(e);
//        }
//        objectOutputStream.writeFloat((Float) HashMapReflectionHelper.callHiddenMethod(map,"loadFactor"));
//        objectOutputStream.writeInt((Integer) HashMapReflectionHelper.callHiddenMethod(map,"capacity"));
    }
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {

objectInputStream.defaultReadObject();
//        Set keys = (Set)objectInputStream.readObject();
//        float loadFactor = objectInputStream.readFloat();
//        int capacity = objectInputStream.readInt();
//        map = new HashMap(capacity, loadFactor);
//        for (Object o:keys){
//            map.put((E)o,new Object());
//        }
    }
}
