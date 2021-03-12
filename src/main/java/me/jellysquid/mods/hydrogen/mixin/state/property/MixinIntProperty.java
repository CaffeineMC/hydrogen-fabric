package me.jellysquid.mods.hydrogen.mixin.state.property;

import me.jellysquid.mods.hydrogen.common.state.property.WeakPropertyCache;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IntProperty.class)
public class MixinIntProperty {
    @Inject(method = "of", at = @At("TAIL"), cancellable = true)
    private static void internOf(CallbackInfoReturnable<IntProperty> cir) {
        cir.setReturnValue(WeakPropertyCache.tryCacheProperty(cir.getReturnValue()));
    }
}
