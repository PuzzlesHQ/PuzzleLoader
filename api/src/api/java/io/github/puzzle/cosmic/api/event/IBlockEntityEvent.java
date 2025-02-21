package io.github.puzzle.cosmic.api.event;

import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;

public interface IBlockEntityEvent {

    IPuzzleBlockEntity getSourceEntity();
    Object getObject();

    static void sendEventToNeighbors(IBlockEntityEvent event) {
//        event.getSourceEntity()._getBlockPosition().offs
    }

}
