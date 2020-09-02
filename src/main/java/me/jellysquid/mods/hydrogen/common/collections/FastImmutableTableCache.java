package me.jellysquid.mods.hydrogen.common.collections;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

import java.util.Arrays;

public class FastImmutableTableCache<R, C, V> {
    private final ObjectOpenCustomHashSet<R[]> rows = new ObjectOpenCustomHashSet<>(new ObjectArrayEqualityStrategy<>());
    private final ObjectOpenCustomHashSet<C[]> columns = new ObjectOpenCustomHashSet<>(new ObjectArrayEqualityStrategy<>());
    private final ObjectOpenCustomHashSet<V[]> values = new ObjectOpenCustomHashSet<>(new ObjectArrayEqualityStrategy<>());

    private final ObjectOpenCustomHashSet<int[]> indices = new ObjectOpenCustomHashSet<>(new IntArrayEqualityStrategy());

    public synchronized V[] dedupValues(V[] values) {
        return this.values.addOrGet(values);
    }

    public synchronized R[] dedupRows(R[] rows) {
        return this.rows.addOrGet(rows);
    }

    public synchronized C[] dedupColumns(C[] columns) {
        return this.columns.addOrGet(columns);
    }

    public synchronized int[] dedupIndices(int[] ints) {
        return this.indices.addOrGet(ints);
    }

    private static class ObjectArrayEqualityStrategy<T> implements Hash.Strategy<T[]> {
        @Override
        public int hashCode(T[] o) {
            return Arrays.hashCode(o);
        }

        @Override
        public boolean equals(T[] a, T[] b) {
            return Arrays.equals(a, b);
        }
    }

    private static class IntArrayEqualityStrategy implements Hash.Strategy<int[]> {
        @Override
        public int hashCode(int[] o) {
            return Arrays.hashCode(o);
        }

        @Override
        public boolean equals(int[] a, int[] b) {
            return Arrays.equals(a, b);
        }
    }

}
