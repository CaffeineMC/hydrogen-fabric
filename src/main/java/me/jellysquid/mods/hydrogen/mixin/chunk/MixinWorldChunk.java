package me.jellysquid.mods.hydrogen.mixin.chunk;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.TickScheduler;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(WorldChunk.class)
public class MixinWorldChunk {
    @Shadow
    @Final
    private ChunkSection[] sections;

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/ChunkPos;" +
            "Lnet/minecraft/world/biome/source/BiomeArray;Lnet/minecraft/world/chunk/UpgradeData;" +
            "Lnet/minecraft/world/TickScheduler;Lnet/minecraft/world/TickScheduler;J[" +
            "Lnet/minecraft/world/chunk/ChunkSection;Ljava/util/function/Consumer;)V",at = @At("RETURN"))
    private void reinit(World world, ChunkPos pos, BiomeArray biomes, UpgradeData upgradeData,
                        TickScheduler<Block> blockTickScheduler, TickScheduler<Fluid> fluidTickScheduler,
                        long inhabitedTime, ChunkSection[] sections, Consumer<WorldChunk> loadToWorldConsumer,
                        CallbackInfo ci) {
        // Upgrading a ProtoChunk to a WorldChunk might result in empty sections being copied over
        // These simply waste memory, and the WorldChunk will return air blocks for any absent section without issue.
        for (int i = 0; i < this.sections.length; i++) {
            if (ChunkSection.isEmpty(this.sections[i])) {
                this.sections[i] = null;
            }
        }
    }
}
