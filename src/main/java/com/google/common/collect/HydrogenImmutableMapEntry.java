package com.google.common.collect;

import java.util.Map;

public class HydrogenImmutableMapEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private final V value;

    public HydrogenImmutableMapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }
}
