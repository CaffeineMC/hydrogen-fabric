package me.jellysquid.mods.hydrogen.mixin.client.model;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Mixin(ModelLoader.class)
public class MixinModelLoader {
    @Mutable
    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> unbakedModels;

    @Mutable
    @Shadow
    @Final
    private Map<Triple<Identifier, AffineTransformation, Boolean>, BakedModel> bakedModelCache;

    @Mutable
    @Shadow
    @Final
    private Map<Identifier, BakedModel> bakedModels;

    @Mutable
    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> modelsToBake;

    @Mutable
    @Shadow
    @Final
    private Set<Identifier> modelsToLoad;

    @Mutable
    @Shadow
    @Final
    private Map<Identifier, Pair<SpriteAtlasTexture, SpriteAtlasTexture.Data>> spriteAtlasData;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int i, CallbackInfo ci) {
        this.unbakedModels = new Object2ObjectOpenHashMap<>(this.unbakedModels);
        this.bakedModelCache = new Object2ObjectOpenHashMap<>(this.bakedModelCache);
        this.bakedModels = new Object2ObjectOpenHashMap<>(this.bakedModels);
        this.modelsToBake = new Object2ObjectOpenHashMap<>(this.modelsToBake);
        this.modelsToLoad = new ObjectOpenHashSet<>(this.modelsToLoad);
        this.spriteAtlasData = new Object2ObjectOpenHashMap<>(this.spriteAtlasData);
    }
}
