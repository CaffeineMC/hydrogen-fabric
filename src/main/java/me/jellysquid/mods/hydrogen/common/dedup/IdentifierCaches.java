package me.jellysquid.mods.hydrogen.common.dedup;

import me.jellysquid.mods.hydrogen.common.HydrogenMod;

public class IdentifierCaches {
    public static final DeduplicationCache<String> NAMESPACES = new DeduplicationCache<>();
    public static final DeduplicationCache<String> PATH = new DeduplicationCache<>();

    public static void printDebug() {
        HydrogenMod.LOGGER.info("[[[ Identifier de-duplication statistics ]]]");
        HydrogenMod.LOGGER.info("Namespace cache: {}", NAMESPACES);
        HydrogenMod.LOGGER.info("Path cache: {}", PATH);
    }
}
