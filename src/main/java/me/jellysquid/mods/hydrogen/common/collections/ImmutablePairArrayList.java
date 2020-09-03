package me.jellysquid.mods.hydrogen.common.collections;

import com.google.common.collect.AbstractIterator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutablePairArrayList<V1, V2> implements List<Pair<V1, V2>> {
    private final V1[] v1;
    private final V2[] v2;
    private final int size;

    private final MutablePair<V1, V2> pair = new MutablePair<>();

    @SuppressWarnings("unchecked")
    public ImmutablePairArrayList(List<Pair<V1, V2>> src) {
        this.size = src.size();

        this.v1 = (V1[]) new Object[this.size];
        this.v2 = (V2[]) new Object[this.size];

        for (int i = 0; i < this.size; i++) {
            Pair<V1, V2> pair = src.get(i);

            this.v1[i] = pair.getKey();
            this.v2[i] = pair.getValue();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o;

        for (int i = 0; i < this.size; i++) {
            if (this.v1[i] == pair.getLeft() && this.v2[i] == pair.getRight()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<Pair<V1, V2>> iterator() {
        return new AbstractIterator<Pair<V1, V2>>() {
            private final V1[] v1 = ImmutablePairArrayList.this.v1;
            private final V2[] v2 = ImmutablePairArrayList.this.v2;
            private final int limit = ImmutablePairArrayList.this.size;

            private final MutablePair<V1, V2> tmp = new MutablePair<>();

            private int index = 0;

            @Override
            protected Pair<V1, V2> computeNext() {
                if (this.index >= this.limit) {
                    return this.endOfData();
                }

                this.tmp.left = this.v1[this.index];
                this.tmp.right = this.v2[this.index];

                this.index++;

                return this.tmp;
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Pair<V1, V2> v1V2Pair) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj : collection) {
            if (!this.contains(obj)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Pair<V1, V2>> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends Pair<V1, V2>> c) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<V1, V2> get(int index) {
        this.pair.left = this.v1[index];
        this.pair.right = this.v2[index];

        return this.pair;
    }

    @Override
    public Pair<V1, V2> set(int index, Pair<V1, V2> element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, Pair<V1, V2> element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<V1, V2> remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<Pair<V1, V2>> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<Pair<V1, V2>> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Pair<V1, V2>> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private static class MutablePair<V1, V2> extends Pair<V1, V2> {
        private V1 left;
        private V2 right;

        @Override
        public V1 getLeft() {
            return this.left;
        }

        @Override
        public V2 getRight() {
            return this.right;
        }

        @Override
        public V2 setValue(V2 value) {
            throw new UnsupportedOperationException();
        }
    }
}
