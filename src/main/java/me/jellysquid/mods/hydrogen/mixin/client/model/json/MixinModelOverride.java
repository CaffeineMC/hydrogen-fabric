package me.jellysquid.mods.hydrogen.mixin.client.model.json;

import it.unimi.dsi.fastutil.objects.Object2FloatLinkedOpenHashMap;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelOverride.class)
public class MixinModelOverride {
    @Mutable
    @Shadow
    @Final
    private Map<Identifier, Float> predicateToThresholds;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(Identifier modelId, Map<Identifier, Float> predicateToThresholds, CallbackInfo ci) {
        this.predicateToThresholds = new Object2FloatLinkedOpenHashMap<>(this.predicateToThresholds);
    }
}
