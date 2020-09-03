package me.jellysquid.mods.hydrogen.mixin.client.model.json;

import com.google.common.collect.Streams;
import me.jellysquid.mods.hydrogen.common.state.StatePropertyPredicateHelper;
import me.jellysquid.mods.hydrogen.common.util.AnyPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.MultipartModelSelector;
import net.minecraft.client.render.model.json.OrMultipartModelSelector;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(OrMultipartModelSelector.class)
public class MixinOrMultipartModelSelector {
    @Shadow @Final private Iterable<? extends MultipartModelSelector> selectors;

    /**
     * @author JellySquid
     * @reason Flatten predicates
     */
    @Overwrite
    public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> stateManager) {
        return StatePropertyPredicateHelper.anyMatch(Streams.stream(this.selectors).map((multipartModelSelector) -> {
            return multipartModelSelector.getPredicate(stateManager);
        }).collect(Collectors.toList()));
    }
}
