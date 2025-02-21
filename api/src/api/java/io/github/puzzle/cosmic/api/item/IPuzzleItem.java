package io.github.puzzle.cosmic.api.item;

import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.APISide;
import io.github.puzzle.cosmic.util.ApiGen;
import io.github.puzzle.cosmic.util.SourceOnly;

@ApiGen("Item")
public interface IPuzzleItem {

    IPuzzleIdentifier _getIdentifier();

    @SourceOnly("Unusable atm so its SourceOnly")
    default void _use(APISide side, IPuzzleItemSlot slot, IPuzzlePlayer player, IPuzzleBlockPosition targetPlaceBlockPos, IPuzzleBlockPosition targetBreakBlockPos, boolean isLeftClick) {
    }

}
