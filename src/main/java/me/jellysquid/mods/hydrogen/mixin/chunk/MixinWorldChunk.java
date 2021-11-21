package me.jellysquid.mods.hydrogen.mixin.chunk;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.BlendingData;
import net.minecraft.world.tick.ChunkTickScheduler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(WorldChunk.class)
public abstract class MixinWorldChunk extends Chunk {

    // This constructor is meaningless, just to make the compiler happy
    public MixinWorldChunk(ChunkPos pos, UpgradeData upgradeData, HeightLimitView heightLimitView, Registry<Biome> biome, long inhabitedTime, @Nullable ChunkSection[] sectionArrayInitializer, @Nullable BlendingData blendingData) {
        super(pos, upgradeData, heightLimitView, biome, inhabitedTime, sectionArrayInitializer, blendingData);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/ChunkPos;" +
            "Lnet/minecraft/world/chunk/UpgradeData;" +
            "Lnet/minecraft/world/tick/ChunkTickScheduler;Lnet/minecraft/world/tick/ChunkTickScheduler;" +
            "J[Lnet/minecraft/world/chunk/ChunkSection;" +
            "Lnet/minecraft/world/chunk/WorldChunk$EntityLoader;Lnet/minecraft/world/gen/chunk/BlendingData;)V",
            at = @At("RETURN"))
    private void reinit(World world, ChunkPos pos,
                        UpgradeData upgradeData,
                        ChunkTickScheduler<Block> blockTickScheduler, ChunkTickScheduler<Fluid> fluidTickScheduler,
                        long inhabitedTime, @Nullable ChunkSection[] sectionArrayInitializer,
                        @Nullable WorldChunk.EntityLoader entityLoader, @Nullable BlendingData blendingData,
                        CallbackInfo ci) {

        // Upgrading a ProtoChunk to a WorldChunk might result in empty sections being copied over
        // These simply waste memory, and the WorldChunk will return air blocks for any absent section without issue.
        for (int i = 0; i < sectionArray.length; i++) {
            if (sectionArray[i].isEmpty()) {
                sectionArray[i] = null;
            }
        }
    }
}
