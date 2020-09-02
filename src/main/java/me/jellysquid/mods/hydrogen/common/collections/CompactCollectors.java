package me.jellysquid.mods.hydrogen.common.collections;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CompactCollectors {
    public static <T> Collector<T, ?, List<T>> toSizedList(int size) {
        return Collectors.toCollection(() -> new ObjectArrayList<>(size));
    }
}
