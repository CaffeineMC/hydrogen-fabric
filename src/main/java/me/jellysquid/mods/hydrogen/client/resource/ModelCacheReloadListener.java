package me.jellysquid.mods.hydrogen.client.resource;

import com.google.common.collect.Lists;
import me.jellysquid.mods.hydrogen.common.dedup.IdentifierCaches;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ModelCacheReloadListener implements SimpleResourceReloadListener<Void> {
    @Override
    public CompletableFuture<Void> load(ResourceManager manager, Profiler profiler, Executor executor) {
        ModelCaches.cleanCaches();

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> apply(Void data, ResourceManager manager, Profiler profiler, Executor executor) {
        ModelCaches.printDebug();
        IdentifierCaches.printDebug();

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("hydrogen", "model_cache_stats");
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return Lists.newArrayList(ResourceReloadListenerKeys.MODELS);
    }
}
