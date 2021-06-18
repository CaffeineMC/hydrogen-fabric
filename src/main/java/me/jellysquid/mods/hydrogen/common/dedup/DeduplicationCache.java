package me.jellysquid.mods.hydrogen.common.dedup;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

import java.util.Objects;

public class DeduplicationCache<T> {
    private final ObjectOpenCustomHashSet<T> cache;

    private int attemptedInsertions = 0;

    public DeduplicationCache(Hash.Strategy<T> strategy) {
        this.cache = new ObjectOpenCustomHashSet<>(strategy);
    }

    public DeduplicationCache() {
        this.cache = new ObjectOpenCustomHashSet<>(new Hash.Strategy<T>() {
            @Override
            public int hashCode(T o) {
                return Objects.hashCode(o);
            }

            @Override
            public boolean equals(T a, T b) {
                return Objects.equals(a, b);
            }
        });
    }

    public synchronized T deduplicate(T item) {
        this.attemptedInsertions++;

        return this.cache.addOrGet(item);
    }

    public void clearCache() {
        this.cache.clear();
        this.attemptedInsertions = 0;
    }

    public int getSize() {
        return this.cache.size();
    }

    public int getDeduplicatedCount() {
        return this.attemptedInsertions - this.cache.size();
    }

    @Override
    public String toString() {
        return String.format("DeduplicationCache ( %d de-duplicated, %d entries )",
                this.getDeduplicatedCount(), this.getSize());
    }
}
