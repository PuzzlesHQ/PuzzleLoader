package io.github.puzzle.cosmic.api.event;

import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
public interface IBlockEntityEvent {

    IPuzzleBlockEntity getSourceEntity();
    Object getObject();

}
