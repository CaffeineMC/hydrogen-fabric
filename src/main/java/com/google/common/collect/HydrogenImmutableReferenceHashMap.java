package com.google.common.collect;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;

import java.util.Map;

import static it.unimi.dsi.fastutil.HashCommon.arraySize;

@SuppressWarnings("unused")
public class HydrogenImmutableReferenceHashMap<K, V> extends ImmutableMap<K, V> {
    protected transient K[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient int size;

    public HydrogenImmutableReferenceHashMap() {

    }

    public HydrogenImmutableReferenceHashMap(Map<K, V> map) {
        this(map.size(), Hash.DEFAULT_LOAD_FACTOR);

        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.putInternal(entry.getKey(), entry.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private HydrogenImmutableReferenceHashMap(final int size, final float loadFactor) {
        if (loadFactor <= 0 || loadFactor > 1) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }

        if (size < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }

        int n = arraySize(size, loadFactor);

        this.key = (K[]) new Object[n];
        this.value = (V[]) new Object[n];
        this.mask = n - 1;
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public V get(final Object k) {
        int pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K curr = this.key[pos];

        // The starting point.
        if (curr == null) {
            return null;
        } else if (k == curr) {
            return this.value[pos];
        }

        // There's always an unused entry.
        while (true) {
            pos = pos + 1 & this.mask;
            curr = this.key[pos];

            if (curr == null) {
                return null;
            } else if (k == curr) {
                return this.value[pos];
            }
        }
    }

    private void putInternal(final K k, final V v) {
        final int pos = this.find(k);

        if (pos < 0) {
            int n = -pos - 1;

            this.key[n] = k;
            this.value[n] = v;
        } else {
            this.value[pos] = v;
        }
    }

    private int find(final K k) {
        int pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K curr = this.key[pos];

        // The starting point.
        if (curr == null) {
            return -(pos + 1);
        } else if (k == curr) {
            return pos;
        }

        // There's always an unused entry.
        while (true) {
            pos = pos + 1 & this.mask;
            curr = this.key[pos];

            if (curr == null) {
                return -(pos + 1);
            } else if (k == curr) {
                return pos;
            }
        }
    }

    @Override
    ImmutableSet<Entry<K, V>> createEntrySet() {
        return new HydrogenEntrySet<>(this.key, this.value, this.size);
    }

    @Override
    ImmutableSet<K> createKeySet() {
        return new ImmutableMapKeySet<>(this);
    }

    @Override
    ImmutableCollection<V> createValues() {
        return new ImmutableMapValues<>(this);
    }

    @Override
    boolean isPartialView() {
        return false;
    }

}
