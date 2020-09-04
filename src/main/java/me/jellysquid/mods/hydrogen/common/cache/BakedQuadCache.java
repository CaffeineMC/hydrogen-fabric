package me.jellysquid.mods.hydrogen.common.cache;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class BakedQuadCache {
    private static final ObjectOpenHashSet<int[]> CACHE = new ObjectOpenHashSet<>();

    public synchronized static int[] dedup(int[] data) {
        return CACHE.addOrGet(data);
    }
}
