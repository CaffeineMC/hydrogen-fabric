package me.jellysquid.mods.hydrogen.mixin.state.property;

import me.jellysquid.mods.hydrogen.common.state.property.WeakPropertyCache;
import net.minecraft.state.property.DirectionProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DirectionProperty.class)
public class MixinDirectionProperty {
    @Inject(method = "of(Ljava/lang/String;Ljava/util/Collection;)Lnet/minecraft/state/property/DirectionProperty;", at = @At("TAIL"), cancellable = true)
    private static void internOf(CallbackInfoReturnable<DirectionProperty> cir) {
        cir.setReturnValue(WeakPropertyCache.tryCacheProperty(cir.getReturnValue()));
    }
}
