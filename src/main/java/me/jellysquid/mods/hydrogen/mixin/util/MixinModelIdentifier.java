package me.jellysquid.mods.hydrogen.mixin.util;

import me.jellysquid.mods.hydrogen.common.cache.IdentifierCache;
import net.minecraft.client.util.ModelIdentifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelIdentifier.class)
public class MixinModelIdentifier {
    @Mutable
    @Shadow
    @Final
    private String variant;

    @Inject(method = "<init>([Ljava/lang/String;)V", at = @At("RETURN"))
    private void reinit(String[] strings, CallbackInfo ci) {
        this.variant = IdentifierCache.dedup(this.variant);
    }
}
