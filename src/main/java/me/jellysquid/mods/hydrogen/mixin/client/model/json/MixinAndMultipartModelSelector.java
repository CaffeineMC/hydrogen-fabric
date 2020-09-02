package me.jellysquid.mods.hydrogen.mixin.client.model.json;

import me.jellysquid.mods.hydrogen.common.collections.CompactCollectors;
import net.minecraft.client.render.model.json.AndMultipartModelSelector;
import net.minecraft.client.render.model.json.MultipartModelSelector;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Mixin(AndMultipartModelSelector.class)
public class MixinAndMultipartModelSelector {
    @Shadow
    @Final
    private Iterable<? extends MultipartModelSelector> selectors;

    @Redirect(method = "getPredicate", at = @At(value = "INVOKE", target = "Ljava/util/stream/Collectors;toList()Ljava/util/stream/Collector;"))
    private <T> Collector<T, ?, List<T>> redirectGetPredicateCollector() {
        if (this.selectors instanceof Collection) {
            return CompactCollectors.toSizedList(((Collection<?>) this.selectors).size());
        }

        return Collectors.toList();
    }
}
