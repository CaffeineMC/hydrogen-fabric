package me.jellysquid.mods.hydrogen.mixin.chunk;

import it.unimi.dsi.fastutil.objects.Reference2ShortMap;
import it.unimi.dsi.fastutil.objects.Reference2ShortOpenHashMap;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.collection.PackedIntegerArray;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeArray.class)
public class MixinBiomeArray {
    @Mutable
    @Shadow
    @Final
    private Biome[] data;

    @Shadow
    @Final
    private IndexedIterable<Biome> field_25831;

    @Shadow
    @Final
    private static int HORIZONTAL_SECTION_COUNT;

    private Biome[] palette;
    private PackedIntegerArray intArray;

    @Inject(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/source/BiomeSource;[I)V", at = @At("RETURN"))
    private void reinit4(IndexedIterable<Biome> indexedIterable, ChunkPos chunkPos, BiomeSource biomeSource, int[] is, CallbackInfo ci) {
        this.createCompact();
    }

    @Inject(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/source/BiomeSource;)V", at = @At("RETURN"))
    private void reinit3(IndexedIterable<Biome> indexedIterable, ChunkPos chunkPos, BiomeSource biomeSource, CallbackInfo ci) {
        this.createCompact();
    }

    @Inject(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;[Lnet/minecraft/world/biome/Biome;)V", at = @At("RETURN"))
    private void reinit2(IndexedIterable<Biome> indexedIterable, Biome[] biomes, CallbackInfo ci) {
        this.createCompact();
    }

    @Inject(method = "<init>(Lnet/minecraft/util/collection/IndexedIterable;[I)V", at = @At("RETURN"))
    private void reinit1(IndexedIterable<Biome> indexedIterable, int[] is, CallbackInfo ci) {
        this.createCompact();
    }

    private void createCompact() {
        if (this.intArray != null || this.data[0] == null) {
            return;
        }

        Reference2ShortOpenHashMap<Biome> paletteTable = this.createPalette();
        Biome[] paletteIndexed = new Biome[paletteTable.size()];

        for (Reference2ShortMap.Entry<Biome> entry : paletteTable.reference2ShortEntrySet()) {
            paletteIndexed[entry.getShortValue()] = entry.getKey();
        }

        int packedIntSize = Math.max(2, MathHelper.log2DeBruijn(paletteTable.size()));
        PackedIntegerArray integerArray = new PackedIntegerArray(packedIntSize, BiomeArray.DEFAULT_LENGTH);

        Biome prevBiome = null;
        short prevId = -1;

        for (int i = 0; i < this.data.length; i++) {
            Biome biome = this.data[i];
            short id;

            if (prevBiome == biome) {
                id = prevId;
            } else {
                id = paletteTable.getShort(biome);

                if (id < 0) {
                    throw new IllegalStateException("Palette is missing entry: " + biome);
                }

                prevId = id;
                prevBiome = biome;
            }

            integerArray.set(i, id);
        }

        this.palette = paletteIndexed;
        this.intArray = integerArray;
        this.data = null;
    }

    private Reference2ShortOpenHashMap<Biome> createPalette() {
        Reference2ShortOpenHashMap<Biome> map = new Reference2ShortOpenHashMap<>();
        map.defaultReturnValue(Short.MIN_VALUE);

        Biome prevObj = null;
        short id = 0;

        for (Biome obj : this.data) {
            if (obj == prevObj) {
                continue;
            }

            if (map.getShort(obj) < 0) {
                map.put(obj, id++);
            }

            prevObj = obj;
        }

        return map;
    }

    /**
     * @author JellySquid
     * @reason Use paletted lookup
     */
    @Overwrite
    public int[] toIntArray() {
        int size = this.intArray.getSize();
        int[] array = new int[size];

        for(int i = 0; i < size; ++i) {
            array[i] = this.field_25831.getRawId(this.palette[this.intArray.get(i)]);
        }

        return array;
    }

    /**
     * @author JellySquid
     * @reason Use paletted lookup
     */
    @Overwrite
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        int x = biomeX & BiomeArray.HORIZONTAL_BIT_MASK;
        int y = MathHelper.clamp(biomeY, 0, BiomeArray.VERTICAL_BIT_MASK);
        int z = biomeZ & BiomeArray.HORIZONTAL_BIT_MASK;

        return this.palette[this.intArray.get(y << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT | z << HORIZONTAL_SECTION_COUNT | x)];
    }
}
