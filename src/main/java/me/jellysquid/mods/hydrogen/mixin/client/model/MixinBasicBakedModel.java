package me.jellysquid.mods.hydrogen.mixin.client.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(BasicBakedModel.class)
public class MixinBasicBakedModel {
    @Mutable
    @Shadow
    @Final
    protected Map<Direction, List<BakedQuad>> faceQuads;

    @Mutable
    @Shadow
    @Final
    protected List<BakedQuad> quads;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(List<BakedQuad> quads, Map<Direction, List<BakedQuad>> faceQuads, boolean usesAo, boolean isSideLit, boolean hasDepth, Sprite sprite, ModelTransformation modelTransformation, ModelOverrideList modelOverrideList, CallbackInfo ci) {
        this.quads = new ObjectArrayList<>(this.quads);

        for (Map.Entry<Direction, List<BakedQuad>> entry : this.faceQuads.entrySet()) {
            entry.setValue(new ObjectArrayList<>(entry.getValue()));
        }
    }
}
