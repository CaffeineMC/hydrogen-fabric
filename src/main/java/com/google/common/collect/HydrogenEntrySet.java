package com.google.common.collect;

import java.util.Map;

class HydrogenEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
    private final K[] key;
    private final V[] value;

    private final int size;

    HydrogenEntrySet(K[] key, V[] value, int size) {
        this.key = key;
        this.value = value;
        this.size = size;
    }

    @Override
    public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
        return new HydrogenEntrySetIterator<>(this.key, this.value, this.size);
    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }
}
