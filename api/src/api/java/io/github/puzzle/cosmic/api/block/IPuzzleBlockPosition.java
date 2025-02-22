package io.github.puzzle.cosmic.api.block;

import io.github.puzzle.cosmic.api.constants.Direction;
import io.github.puzzle.cosmic.api.event.IBlockUpdateEvent;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("BlockPosition")
public interface IPuzzleBlockPosition {

    int _getGlobalX();
    int _getGlobalY();
    int _getGlobalZ();

    int _getLocalX();
    int _getLocalY();
    int _getLocalZ();

    @Nullable
    IPuzzleChunk _getChunk();

    @Nullable
    IPuzzleZone _getZone();

    @Nullable
    IPuzzleBlockEntity _getBlockEntity();
    IPuzzleBlockEntity _setBlockEntity(IPuzzleBlockState state);

    default boolean _hasBlockEntity() {
        return _getBlockEntity() != null;
    }

    IPuzzleBlockPosition _set(IPuzzleChunk chunk, int localX, int localY, int localZ);

    void _convertToLocal(IPuzzleZone zone);
    void _setGlobal(IPuzzleZone zone, float x, float y, float z);

    IPuzzleBlockState _getBlockState();
    void _setBlockState(IPuzzleBlockState state);

    int _getSkylight();

    void _updateNeighbors(IBlockUpdateEvent event);
    void _updateNeighborInDirection(IBlockUpdateEvent event, Direction direction);

    IPuzzleBlockPosition _getOffsetBlockPos(IPuzzleZone zone, int offsetX, int offsetY, int offsetZ);
    IPuzzleBlockPosition _getOffsetBlockPos(int offsetX, int offsetY, int offsetZ);
    IPuzzleBlockPosition _getOffsetBlockPos(IPuzzleBlockPosition destBlockPos, IPuzzleZone zone, int offsetX, int offsetY, int offsetZ);
    IPuzzleBlockPosition _getOffsetBlockPos(IPuzzleZone zone, Direction d);
    IPuzzleBlockPosition _getOffsetBlockPos(IPuzzleBlockPosition destBlockPos, IPuzzleZone zone, Direction d);
}
