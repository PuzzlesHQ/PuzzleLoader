package io.github.puzzle.cosmic.impl.event;

import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;
import io.github.puzzle.cosmic.api.event.IBlockEntityEvent;

public class BlockEntityEvent implements IBlockEntityEvent {

    private final IPuzzleBlockEntity entity;
    private final Object o;

    public BlockEntityEvent(IPuzzleBlockEntity entity) {
        this.entity = entity;
        this.o = null;
    }

    public BlockEntityEvent(IPuzzleBlockEntity entity, Object o) {
        this.entity = entity;
        this.o = o;
    }

    public static IBlockEntityEvent of(IPuzzleBlockEntity entity) {
        return new BlockEntityEvent(entity);
    }

    public static IBlockEntityEvent of(IPuzzleBlockEntity entity, Object o) {
        return new BlockEntityEvent(entity, o);
    }

    @Override
    public IPuzzleBlockEntity getSourceEntity() {
        return entity;
    }

    @Override
    public Object getObject() {
        return o;
    }
}
