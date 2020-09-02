package me.jellysquid.mods.hydrogen.common.collections;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

import java.util.Arrays;

public class FastImmutableTableCache<R, C, V> {
    private final ObjectOpenCustomHashSet<R[]> rows = new ObjectOpenCustomHashSet<>(new Hash.Strategy<R[]>() {
        @Override
        public int hashCode(R[] o) {
            return Arrays.hashCode(o);
        }

        @Override
        public boolean equals(R[] a, R[] b) {
            return Arrays.equals(a, b);
        }
    });

    private final ObjectOpenCustomHashSet<C[]> columns = new ObjectOpenCustomHashSet<>(new Hash.Strategy<C[]>() {
        @Override
        public int hashCode(C[] o) {
            return Arrays.hashCode(o);
        }

        @Override
        public boolean equals(C[] a, C[] b) {
            return Arrays.equals(a, b);
        }
    });

    private final ObjectOpenCustomHashSet<V[]> values = new ObjectOpenCustomHashSet<>(new Hash.Strategy<V[]>() {
        @Override
        public int hashCode(V[] o) {
            return Arrays.hashCode(o);
        }

        @Override
        public boolean equals(V[] a, V[] b) {
            return Arrays.equals(a, b);
        }
    });


    private final ObjectOpenCustomHashSet<int[]> ints = new ObjectOpenCustomHashSet<>(new Hash.Strategy<int[]>() {
        @Override
        public int hashCode(int[] o) {
            return Arrays.hashCode(o);
        }

        @Override
        public boolean equals(int[] a, int[] b) {
            return Arrays.equals(a, b);
        }
    });

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
        return this.ints.addOrGet(ints);
    }
}
