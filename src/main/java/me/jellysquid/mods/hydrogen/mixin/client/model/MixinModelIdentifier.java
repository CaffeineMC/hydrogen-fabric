package me.jellysquid.mods.hydrogen.mixin.client.model;

import me.jellysquid.mods.hydrogen.client.model.HydrogenModelIdentifier;
import me.jellysquid.mods.hydrogen.client.resource.ModelCaches;
import net.minecraft.client.util.ModelIdentifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ModelIdentifier.class)
public class MixinModelIdentifier implements HydrogenModelIdentifier {
    @Mutable
    @Shadow
    @Final
    private String variant;

    private String[] properties;

    @Inject(method = "<init>([Ljava/lang/String;)V", at = @At("RETURN"))
    private void reinit(String[] strings, CallbackInfo ci) {
        this.properties = Arrays.stream(this.variant.split(","))
                .map(ModelCaches.PROPERTIES::deduplicate)
                .toArray(String[]::new);

        // Poison any code path using this variable.
        this.variant = null;
    }

    /**
     * @author JellySquid
     * @reason Use properties array
     */
    @Overwrite
    public String getVariant() {
        return String.join(",", this.properties);
    }

    /**
     * @author JellySquid
     * @reason Use properties array
     */
    @Overwrite
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ModelIdentifier && super.equals(o)) {
            return Arrays.equals(this.properties, ((HydrogenModelIdentifier) o).getProperties());
        } else {
            return false;
        }
    }

    /**
     * @author JellySquid
     * @reason Use properties array
     */
    @Overwrite
    public int hashCode() {
        return 31 * super.hashCode() + Arrays.hashCode(this.properties);
    }

    /**
     * @author JellySquid
     * @reason Use properties array
     */
    @Overwrite
    public String toString() {
        return super.toString() + "#" + this.getVariant();
    }

    @Override
    public String[] getProperties() {
        return this.properties;
    }
}
