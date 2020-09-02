package me.jellysquid.mods.hydrogen.mixin.state;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import com.mojang.serialization.MapCodec;
import me.jellysquid.mods.hydrogen.common.cache.StatePropertyTableCache;
import me.jellysquid.mods.hydrogen.common.collections.FastImmutableTable;
import me.jellysquid.mods.hydrogen.common.jvm.ClassConstructors;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(State.class)
public class MixinState<O, S> {
    @Mutable
    @Shadow
    @Final
    private ImmutableMap<Property<?>, Comparable<?>> entries;

    @Shadow
    private Table<Property<?>, Comparable<?>, S> withTable;

    @Shadow
    @Final
    protected O owner;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(O owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<S> mapCodec, CallbackInfo ci) {
        this.entries = ClassConstructors.createFastImmutableMap(this.entries);
    }

    @Inject(method = "createWithTable", at = @At("RETURN"))
    private void postCreateWithTable(Map<Map<Property<?>, Comparable<?>>, S> states, CallbackInfo ci) {
        this.withTable = new FastImmutableTable<>(this.withTable, StatePropertyTableCache.getTableCache(this.owner));
    }
}
