package me.jellysquid.mods.hydrogen.client.resource;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import me.jellysquid.mods.hydrogen.common.HydrogenMod;
import me.jellysquid.mods.hydrogen.common.dedup.DeduplicationCache;

public class ModelCaches {
    public static final DeduplicationCache<int[]> QUADS = new DeduplicationCache<>(IntArrays.HASH_STRATEGY);
    public static final DeduplicationCache<String> PROPERTIES = new DeduplicationCache<>();

    public static void printDebug() {
        HydrogenMod.LOGGER.info("[[[ Model de-duplication statistics ]]]");
        HydrogenMod.LOGGER.info("Baked quad cache: {}", QUADS);
        HydrogenMod.LOGGER.info("Properties cache: {}", PROPERTIES);
    }

    public static void cleanCaches() {
        QUADS.clearCache();
    }
}
