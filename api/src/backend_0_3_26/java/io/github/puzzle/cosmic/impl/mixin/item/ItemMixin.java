package io.github.puzzle.cosmic.impl.mixin.item;

import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.item.IPuzzleItem;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.Internal;
import org.spongepowered.asm.mixin.Mixin;

@Internal
@Mixin(Item.class)
public interface ItemMixin extends IPuzzleItem {

    @Override
    default IPuzzleIdentifier _getIdentifier() {
        Item item = IPuzzleItem.as(this);

        return IPuzzleIdentifier.as(Identifier.of(item.getID()));
    }

}
