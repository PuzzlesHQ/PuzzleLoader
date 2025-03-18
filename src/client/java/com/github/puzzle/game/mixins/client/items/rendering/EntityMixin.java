package com.github.puzzle.game.mixins.client.items.rendering;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.github.puzzle.core.loader.util.Reflection;
import com.github.puzzle.game.engine.items.model.ItemModelWrapper;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.rendering.entities.IEntityModelInstance;
import finalforeach.cosmicreach.rendering.entities.instances.ItemEntityModelInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow public IEntityModelInstance modelInstance;

    @Shadow @Final protected static Matrix4 tmpModelMatrix;

    @Shadow public Vector3 position;
    @Inject(method = "renderModelAfterMatrixSet", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/rendering/entities/IEntityModelInstance;render(Lfinalforeach/cosmicreach/entities/Entity;Lcom/badlogic/gdx/graphics/Camera;Lcom/badlogic/gdx/math/Matrix4;Z)V", shift = At.Shift.BEFORE), cancellable = true)
    private void render(Camera worldCamera, boolean shouldRender, CallbackInfo ci) {
        if (modelInstance instanceof ItemEntityModelInstance) {
            if (Reflection.getFieldContents(modelInstance, "model") instanceof ItemModelWrapper m) {
                ItemStack stack = Reflection.getFieldContents(this, "itemStack");
                m.renderAsEntity(position, stack, worldCamera, tmpModelMatrix);
                ci.cancel();
            }
        }
    }

}
