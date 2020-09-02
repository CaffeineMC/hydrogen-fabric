package me.jellysquid.mods.hydrogen.mixin.client.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.MultipartBakedModel;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.function.Predicate;

@Mixin(MultipartBakedModel.class)
public class MixinMultipartBakedModel {
    @Mutable
    @Shadow
    @Final
    private List<Pair<Predicate<BlockState>, BakedModel>> components;

    @Mutable
    @Shadow
    @Final
    private Map<BlockState, BitSet> stateCache;

    private List<Predicate<BlockState>> componentPredicates;
    private List<BakedModel> componentModels;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(List<Pair<Predicate<BlockState>, BakedModel>> components, CallbackInfo ci) {
        this.stateCache = new Reference2ObjectOpenHashMap<>(this.stateCache);

        this.componentPredicates = new ObjectArrayList<>(this.components.size());
        this.componentModels = new ObjectArrayList<>(this.components.size());

        for (Pair<Predicate<BlockState>, BakedModel> pair : this.components) {
            this.componentPredicates.add(pair.getKey());
            this.componentModels.add(pair.getValue());
        }

        this.components.clear();
        this.components = null;
    }

    /**
     * @author JellySquid
     * @reason Avoid indirection and Pair overhead
     * TODO: Avoid using Overwrite... maybe hack at the List implementation?
     */
    @Overwrite
    public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
        if (state == null) {
            return Collections.emptyList();
        }

        BitSet bitSet = this.stateCache.get(state);

        if (bitSet == null) {
            bitSet = new BitSet();

            for (int i = 0; i < this.componentPredicates.size(); ++i) {
                Predicate<BlockState> predicate = this.componentPredicates.get(i);

                if (predicate.test(state)) {
                    bitSet.set(i);
                }
            }

            this.stateCache.put(state, bitSet);
        }

        List<BakedQuad> list = Lists.newArrayList();
        long seed = random.nextLong();

        for (int j = 0; j < bitSet.length(); ++j) {
            if (bitSet.get(j)) {
                list.addAll(this.componentModels.get(j).getQuads(state, face, new Random(seed)));
            }
        }

        return list;
    }
}
