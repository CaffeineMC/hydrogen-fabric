package me.jellysquid.mods.hydrogen.mixin.state.property;

import me.jellysquid.mods.hydrogen.common.state.property.WeakPropertyCache;
import net.minecraft.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BooleanProperty.class)
public class MixinBooleanProperty {
    @Inject(method = "of", at = @At("TAIL"), cancellable = true)
    private static void internOf(CallbackInfoReturnable<BooleanProperty> cir) {
        cir.setReturnValue(WeakPropertyCache.tryCacheProperty(cir.getReturnValue()));
    }
}
