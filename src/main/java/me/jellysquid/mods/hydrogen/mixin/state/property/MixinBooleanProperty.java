package me.jellysquid.mods.hydrogen.mixin.state.property;

import com.google.common.base.FinalizableWeakReference;
import me.jellysquid.mods.hydrogen.common.state.property.FinalizableWeakReferenceToCachedProperty;
import net.minecraft.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mixin(BooleanProperty.class)
public class MixinBooleanProperty {
    /**
     * Required synchronized map because background GC threads may clear entries per {@link FinalizableWeakReference}.
     */
    private static final Map<String, WeakReference<BooleanProperty>> cache = Collections.synchronizedMap(new HashMap<>());

    @Inject(method = "of", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private static void internOf(String name, CallbackInfoReturnable<BooleanProperty> cir) {
        BooleanProperty newProperty = cir.getReturnValue();

        WeakReference<BooleanProperty> propertyRef = cache.computeIfAbsent(name, s -> {
            System.err.println("Property \"" + s + "\" was missing, adding to cache!");
            return new FinalizableWeakReferenceToCachedProperty<>(newProperty, cache);
        });

        cir.setReturnValue(propertyRef.get());
    }
}
