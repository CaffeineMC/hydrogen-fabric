package me.jellysquid.mods.hydrogen.common.cache;

import java.util.Arrays;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class BakedQuadCache {
    private static final Int2ObjectOpenHashMap<int[]> CACHE = new Int2ObjectOpenHashMap<>();

    public synchronized static int[] dedup(int[] data) {
        int[] ret = CACHE.putIfAbsent(Arrays.hashCode(data), data);
        if(ret == null)ret = data;
        return ret;
    }
}
