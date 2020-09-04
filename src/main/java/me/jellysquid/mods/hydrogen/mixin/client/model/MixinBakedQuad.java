package me.jellysquid.mods.hydrogen.mixin.client.model;

import me.jellysquid.mods.hydrogen.common.cache.BakedQuadCache;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BakedQuad.class)
public class MixinBakedQuad {
    @Mutable
    @Shadow
    @Final
    protected int[] vertexData;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(int[] vertexData, int colorIndex, Direction face, Sprite sprite, boolean shade, CallbackInfo ci) {
        this.vertexData = BakedQuadCache.dedup(this.vertexData);
    }
}
