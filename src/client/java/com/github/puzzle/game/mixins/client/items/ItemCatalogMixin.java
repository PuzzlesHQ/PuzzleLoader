package com.github.puzzle.game.mixins.client.items;

import com.badlogic.gdx.utils.Array;
import com.github.puzzle.game.items.IModItem;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.ui.widgets.ItemCatalogWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemCatalogWidget.class)
public class ItemCatalogMixin {

    @Redirect(method = "getItems", at = @At(value = "INVOKE", target = "Lcom/badlogic/gdx/utils/Array;add(Ljava/lang/Object;)V"))
    private <T> void nuhUh(Array instance, T value) {
        if (value instanceof IModItem item) {
            if (!item.isDebug()) instance.add(item);
        } else instance.add(value);
    }

}
