package me.jellysquid.mods.hydrogen.common.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectionHelper {
    public static <T> List<T> fixed(List<T> src) {
        return new FixedArrayList<>(src);
    }

    public static <T> Collector<T, ?, List<T>> toSizedList(int size) {
        return Collectors.toCollection(() -> new ArrayList<>(size));
    }
}
