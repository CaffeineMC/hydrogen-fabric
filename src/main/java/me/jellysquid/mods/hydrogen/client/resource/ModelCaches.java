package me.jellysquid.mods.hydrogen.client.resource;

import it.unimi.dsi.fastutil.ints.IntArrays;
import me.jellysquid.mods.hydrogen.common.HydrogenMod;
import me.jellysquid.mods.hydrogen.common.dedup.DeduplicationCache;

public class ModelCaches {
    public static final DeduplicationCache<int[]> QUADS = new DeduplicationCache<>(IntArrays.HASH_STRATEGY);

    public static void printDebug() {
        HydrogenMod.LOGGER.info("[[[ Deduplication statistics ]]]");
        HydrogenMod.LOGGER.info("Baked quad cache: {}", QUADS);
    }

    public static void cleanCaches() {
        QUADS.clearCache();
    }
}
