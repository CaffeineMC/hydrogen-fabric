package me.jellysquid.mods.hydrogen.common.collections;

import com.google.common.collect.Table;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static it.unimi.dsi.fastutil.HashCommon.arraySize;

public class FastImmutableTable<R, C, V> implements Table<R, C, V> {
    private R[] rowKeys;
    private int[] rowIndices;
    private final int rowMask;
    private final int rowCount;

    private C[] columnKeys;
    private int[] columnIndices;
    private final int columnMask;
    private final int columnCount;

    private V[] values;
    private final int size;

    @SuppressWarnings("unchecked")
    public FastImmutableTable(Table<R, C, V> table, FastImmutableTableCache<R, C, V> cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache must not be null");
        }

        float loadFactor = Hash.DEFAULT_LOAD_FACTOR;

        Set<R> rowKeySet = table.rowKeySet();
        Set<C> columnKeySet = table.columnKeySet();

        this.rowCount = rowKeySet.size();
        this.columnCount = columnKeySet.size();

        if (this.rowCount > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Too many rows!");
        } else if (this.columnCount > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Too many columns!");
        }

        int rowN = arraySize(this.rowCount, loadFactor);
        int columnN = arraySize(this.columnCount, loadFactor);

        this.rowMask = rowN - 1;
        this.rowKeys = (R[]) new Object[rowN];
        this.rowIndices = new int[rowN];

        this.columnMask = columnN - 1;
        this.columnKeys = (C[]) new Object[columnN];
        this.columnIndices = new int[columnN];

        this.createIndex(this.columnKeys, this.columnIndices, this.columnMask, columnKeySet);
        this.createIndex(this.rowKeys, this.rowIndices, this.rowMask, rowKeySet);

        this.values = (V[]) new Object[this.rowCount * this.columnCount];

        for (Cell<R, C, V> cell : table.cellSet()) {
            int keyIdx = this.getIndex(this.columnKeys, this.columnIndices, this.columnMask, cell.getColumnKey());
            int rowIdx = this.getIndex(this.rowKeys, this.rowIndices, this.rowMask, cell.getRowKey());

            if (keyIdx < 0 || rowIdx < 0) {
                throw new IllegalStateException("Missing index for " + cell);
            }

            this.values[(this.columnCount * rowIdx) + keyIdx] = cell.getValue();
        }

        this.size = table.size();

        this.rowKeys = cache.dedupRows(this.rowKeys);
        this.rowIndices = cache.dedupIndices(this.rowIndices);

        this.columnIndices = cache.dedupIndices(this.columnIndices);
        this.columnKeys = cache.dedupColumns(this.columnKeys);

        this.values = cache.dedupValues(this.values);
    }

    private <T> void createIndex(T[] keys, int[] indices, int mask, Collection<T> iterable) {
        int j = 0;

        for (T obj : iterable) {
            int i = this.find(keys, mask, obj);

            if (i < 0) {
                keys[i = -i - 1] = obj;
            }

            indices[i] = j++;
        }
    }

    private <T> int getIndex(T[] keys, int[] indices, int mask, T key) {
        int pos = this.find(keys, mask, key);

        if (pos < 0) {
            return -1;
        }

        return indices[pos];
    }

    @Override
    public boolean contains(Object rowKey, Object columnKey) {
        return this.get(rowKey, columnKey) != null;
    }

    @Override
    public boolean containsRow(Object rowKey) {
        return this.find(this.rowKeys, this.rowMask, rowKey) >= 0;
    }

    @Override
    public boolean containsColumn(Object columnKey) {
        return this.find(this.columnKeys, this.columnMask, columnKey) >= 0;
    }

    @Override
    public boolean containsValue(Object value) {
        return ArrayUtils.contains(this.values, value);
    }

    @Override
    public V get(Object rowKey, Object columnKey) {
        final int row = this.getIndex(this.rowKeys, this.rowIndices, this.rowMask, rowKey);
        final int column = this.getIndex(this.columnKeys, this.columnIndices, this.columnMask, columnKey);

        if (row < 0 || column < 0) {
            return null;
        }

        return this.values[(this.columnCount * row) + column];
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V put(R rowKey, C columnKey, V val) {
        throw new UnsupportedOperationException();
    }

    private <T> int find(T[] key, int mask, T value) {
        T curr;
        int pos;

        // The starting point.
        if ((curr = key[pos = HashCommon.mix(System.identityHashCode(value)) & mask]) == null) {
            return -(pos + 1);
        }
        if (value == curr) {
            return pos;
        }
        // There's always an unused entry.
        while (true) {
            if ((curr = key[pos = pos + 1 & mask]) == null) {
                return -(pos + 1);
            }
            if (value == curr) {
                return pos;
            }
        }
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object rowKey, Object columnKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<C, V> row(R rowKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<R, V> column(C columnKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Cell<R, C, V>> cellSet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<R> rowKeySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<C> columnKeySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<R, Map<C, V>> rowMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<C, Map<R, V>> columnMap() {
        throw new UnsupportedOperationException();
    }
}
