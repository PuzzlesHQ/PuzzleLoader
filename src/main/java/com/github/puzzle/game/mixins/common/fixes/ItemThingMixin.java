package com.github.puzzle.game.mixins.common.fixes;

import com.badlogic.gdx.utils.ObjectMap;
import com.github.puzzle.game.PuzzleRegistries;
import finalforeach.cosmicreach.items.ItemThing;
import finalforeach.cosmicreach.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author CrabKing
 * @reason Fixes fluid vacuum not using PuzzleRegistries Items.
 */
@Mixin(targets = {"finalforeach/cosmicreach/items/ItemThing$3"})
public class ItemThingMixin extends ItemThing {

    @Redirect(method = "useItem", at = @At(value = "INVOKE", target = "Lcom/badlogic/gdx/utils/ObjectMap;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object redirectGetLatexItem(ObjectMap instance, Object key) {
        if (key instanceof String id){
            return PuzzleRegistries.ITEMS.get(Identifier.of(id));
        }
        return null;
    }
}
