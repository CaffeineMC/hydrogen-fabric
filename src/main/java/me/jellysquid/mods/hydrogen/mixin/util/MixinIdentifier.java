package me.jellysquid.mods.hydrogen.mixin.util;

import me.jellysquid.mods.hydrogen.common.dedup.IdentifierCaches;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Identifier.class)
public class MixinIdentifier {
    @Mutable
    @Shadow
    @Final
    protected String namespace;

    @Mutable
    @Shadow
    @Final
    protected String path;

    @Inject(method = "<init>([Ljava/lang/String;)V", at = @At("RETURN"))
    private void reinit(String[] id, CallbackInfo ci) {
        this.namespace = IdentifierCaches.NAMESPACES.deduplicate(this.namespace);
        this.path = IdentifierCaches.PATH.deduplicate(this.path);
    }
}
