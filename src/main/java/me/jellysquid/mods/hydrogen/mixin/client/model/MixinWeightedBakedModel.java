package me.jellysquid.mods.hydrogen.mixin.client.model;

import me.jellysquid.mods.hydrogen.common.collections.CollectionHelper;
import net.minecraft.client.render.model.WeightedBakedModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(WeightedBakedModel.class)
public class MixinWeightedBakedModel {
    @Mutable
    @Shadow
    @Final
    private List<WeightedBakedModel.Entry> models;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(List<?> models, CallbackInfo ci) {
        this.models = CollectionHelper.fixed(this.models);
    }
}
