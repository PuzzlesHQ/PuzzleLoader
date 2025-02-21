package io.github.puzzle.cosmic.api.event;

import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IBlockUpdateEvent {

    // Will get overridden
    @NotNull IPuzzleBlockPosition getSourcePosition();
    @Nullable Object getAttachedObject();

    void setSourcePosition(@NotNull IPuzzleBlockPosition position);
    void setAttachedObject(@Nullable Object o);

}
