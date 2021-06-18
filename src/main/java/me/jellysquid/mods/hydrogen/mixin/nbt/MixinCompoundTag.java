package me.jellysquid.mods.hydrogen.mixin.nbt;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(NbtCompound.class)
public class MixinCompoundTag {
    @Mutable
    @Shadow
    @Final
    private Map<String, NbtElement> entries;

    @Inject(method = "<init>(Ljava/util/Map;)V", at = @At("RETURN"))
    private void reinit(Map<String, NbtElement> tags, CallbackInfo ci) {
        this.entries = tags instanceof Object2ObjectMap ? tags : new Object2ObjectOpenHashMap<>(tags);
    }
}
