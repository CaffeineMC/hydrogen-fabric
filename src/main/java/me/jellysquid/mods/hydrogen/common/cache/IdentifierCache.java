package me.jellysquid.mods.hydrogen.common.cache;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class IdentifierCache {
    private static final ObjectOpenHashSet<String> CACHE = new ObjectOpenHashSet<>();

    public static synchronized String dedup(String value) {
        return CACHE.addOrGet(value);
    }
}
