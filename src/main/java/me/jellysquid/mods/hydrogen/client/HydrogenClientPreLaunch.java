package me.jellysquid.mods.hydrogen.client;

import me.jellysquid.mods.hydrogen.client.resource.ModelCacheReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class HydrogenClientPreLaunch {
    public static void init() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
                .registerReloadListener(new ModelCacheReloadListener());
    }
}
