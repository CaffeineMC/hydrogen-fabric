package me.jellysquid.mods.hydrogen.common.collections;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class FixedArrayList<T> implements List<T> {
    private T[] array;

    @SuppressWarnings("unchecked")
    public FixedArrayList(List<T> list) {
        this(list.toArray((T[]) new Object[0]));
    }

    public FixedArrayList(T[] array) {
        this.array = array;
    }

    @Override
    public int size() {
        return this.array.length;
    }

    @Override
    public boolean isEmpty() {
        return this.array.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return ArrayUtils.contains(this.array, o);
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.forArray(this.array);
    }

    @Override
    public Object[] toArray() {
        return this.array.clone();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] dst) {
        T[] src = this.array;

        if (dst.length < src.length) {
            return (T1[]) Arrays.copyOf(src, src.length, dst.getClass());
        }

        System.arraycopy(src, 0, dst, 0, src.length);

        if (dst.length > src.length) {
            dst[src.length] = null;
        }

        return dst;
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!ArrayUtils.contains(this.array, o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        Arrays.fill(this.array, null);
        this.array = (T[]) new Object[this.array.length]
    }

    @Override
    public T get(int index) {
        return this.array[index];
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        return ArrayUtils.indexOf(this.array, o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return ArrayUtils.lastIndexOf(this.array, o);
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
