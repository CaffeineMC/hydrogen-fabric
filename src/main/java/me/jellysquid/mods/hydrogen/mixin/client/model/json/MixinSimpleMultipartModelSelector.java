package me.jellysquid.mods.hydrogen.mixin.client.model.json;

import com.google.common.base.Splitter;
import me.jellysquid.mods.hydrogen.common.collections.CollectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.SimpleMultipartModelSelector;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
@Mixin(SimpleMultipartModelSelector.class)
public class MixinSimpleMultipartModelSelector {
    private static final ThreadLocal<List<String>> CAPTURED_LISTS = new ThreadLocal<>();

    @Shadow
    @Final
    private String key;

    @Shadow
    @Final
    private String valueString;

    @Redirect(method = "getPredicate", at = @At(value = "INVOKE", target = "Lcom/google/common/base/Splitter;splitToList(Ljava/lang/CharSequence;)Ljava/util/List;", remap = false))
    private List<String> captureSplitList(Splitter splitter, CharSequence sequence) {
        List<String> list = splitter.splitToList(sequence);

        CAPTURED_LISTS.set(list);

        return list;
    }

    /**
     * @author JellySquid
     */
    @Redirect(method = "getPredicate", at = @At(value = "INVOKE", target = "Ljava/util/stream/Collectors;toList()Ljava/util/stream/Collector;"))
    private <T> Collector<T, ?, List<T>> redirectGetPredicateCollector() {
        List<String> list = CAPTURED_LISTS.get();

        if (list != null) {
            CAPTURED_LISTS.remove();

            return CollectionHelper.toSizedList(list.size());
        }

        return Collectors.toList();
    }

    /**
     * @author JellySquid
     * @reason Avoid capturing the entire Optional
     */
    @Overwrite
    private Predicate<BlockState> createPredicate(StateManager<Block, BlockState> stateFactory, Property<?> property, String valueString) {
        Object value = property.parse(valueString)
                .orElse(null);

        if (value == null) {
            throw new RuntimeException(String.format("Unknown value '%s' for property '%s' on '%s' in '%s'",
                    valueString, this.key, stateFactory.getOwner().toString(), this.valueString));
        } else {
            return (blockState) -> blockState.get(property).equals(value);
        }
    }
}
