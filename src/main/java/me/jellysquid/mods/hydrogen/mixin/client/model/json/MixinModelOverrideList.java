package me.jellysquid.mods.hydrogen.mixin.client.model.json;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.client.render.model.json.ModelOverrideList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ModelOverrideList.class)
public class MixinModelOverrideList {
    @Mutable
    @Shadow
    @Final
    private List<ModelOverride> overrides;

    @Mutable
    @Shadow
    @Final
    private List<BakedModel> models;

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void reinit(CallbackInfo ci) {
        this.overrides = new ArrayList<>(this.overrides);
        this.models = new ArrayList<>(this.models);
    }
}
