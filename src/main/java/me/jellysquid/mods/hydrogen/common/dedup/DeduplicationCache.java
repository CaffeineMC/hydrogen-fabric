package me.jellysquid.mods.hydrogen.common.dedup;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

public class DeduplicationCache<T> {
    private final ObjectOpenCustomHashSet<T> cache;

    private int size = 0;

    public DeduplicationCache(Hash.Strategy<T> strategy) {
        this.cache = new ObjectOpenCustomHashSet<>(strategy);
    }

    public synchronized T deduplicate(T item) {
        this.size++;

        return this.cache.addOrGet(item);
    }

    public void clearCache() {
        this.cache.clear();
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public int getDeduplicatedCount() {
        return this.size - this.cache.size();
    }

    @Override
    public String toString() {
        return String.format("DeduplicationCache ( %d de-duplicated, %d entries )",
                this.getDeduplicatedCount(), this.getSize());
    }
}
