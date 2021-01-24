package me.jellysquid.mods.hydrogen.client;

import me.jellysquid.mods.hydrogen.client.resource.ModelCacheReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class HydrogenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
                .registerReloadListener(new ModelCacheReloadListener());
    }
}
