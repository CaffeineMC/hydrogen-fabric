package me.jellysquid.mods.hydrogen.common.cache;

import me.jellysquid.mods.hydrogen.common.collections.FastImmutableTableCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.property.Property;

public class StatePropertyTableCache {
    public static final FastImmutableTableCache<Property<?>, Comparable<?>, BlockState> BLOCK_STATE_TABLE =
            new FastImmutableTableCache<>();

    public static final FastImmutableTableCache<Property<?>, Comparable<?>, FluidState> FLUID_STATE_TABLE =
            new FastImmutableTableCache<>();

    @SuppressWarnings("unchecked")
    public static <S, O> FastImmutableTableCache<Property<?>, Comparable<?>, S> getTableCache(O owner) {
        if (owner instanceof Block) {
            return (FastImmutableTableCache<Property<?>, Comparable<?>, S>) BLOCK_STATE_TABLE;
        } else if (owner instanceof Fluid) {
            return (FastImmutableTableCache<Property<?>, Comparable<?>, S>) FLUID_STATE_TABLE;
        } else {
            return null;
        }
    }
}
