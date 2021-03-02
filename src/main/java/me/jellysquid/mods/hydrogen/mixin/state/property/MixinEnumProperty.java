package me.jellysquid.mods.hydrogen.mixin.state.property;

import me.jellysquid.mods.hydrogen.common.state.property.WeakPropertyCache;
import net.minecraft.state.property.EnumProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnumProperty.class)
public class MixinEnumProperty {
    @Inject(method = "of(Ljava/lang/String;Ljava/lang/Class;Ljava/util/Collection;)Lnet/minecraft/state/property/EnumProperty;", at = @At("TAIL"), cancellable = true)
    private static void internOf(CallbackInfoReturnable<EnumProperty<?>> cir) {
        cir.setReturnValue(WeakPropertyCache.tryCacheProperty(cir.getReturnValue()));
    }
}
