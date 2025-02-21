package io.github.puzzle.cosmic.api.event;

import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
public interface IBlockEntityUpdateEvent extends IBlockUpdateEvent {

    default IPuzzleBlockEntity getSourceEntity() {
        return getSourcePosition()._getBlockEntity();
    }

}
