package me.jellysquid.mods.hydrogen.common.cache;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

public class BakedQuadCache {
    private static final ObjectOpenCustomHashSet<int[]> CACHE = new ObjectOpenCustomHashSet<>(IntArrays.HASH_STRATEGY);

    public synchronized static int[] dedup(int[] data) {
        return CACHE.addOrGet(data);
    }
}
