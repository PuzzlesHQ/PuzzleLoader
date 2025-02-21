package io.github.puzzle.cosmic.api.block;

import com.badlogic.gdx.utils.Pool;
import io.github.puzzle.cosmic.api.constants.Direction;
import io.github.puzzle.cosmic.api.event.IBlockEntityEvent;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.ApiGen;

@ApiGen("BlockPosition")
public interface IPuzzleBlockPosition {

    int _getGlobalX();
    int _getGlobalY();
    int _getGlobalZ();

    int _getLocalX();
    int _getLocalY();
    int _getLocalZ();

    IPuzzleChunk _getChunk();
    IPuzzleZone _getZone();

    IPuzzleBlockEntity _getBlockEntity();
    IPuzzleBlockEntity _setBlockEntity(IPuzzleBlockState state);

    IPuzzleBlockPosition _set(IPuzzleChunk chunk, int localX, int localY, int localZ);

    void _convertToLocal(IPuzzleZone zone);
    void _setGlobal(IPuzzleZone zone, float x, float y, float z);

    IPuzzleBlockState _getBlockState();
    void _setBlockState(IPuzzleBlockState state);

    int _getSkylight();

    void _alertNeighbors(IBlockEntityEvent event);
    void _sendEventToNeighbors(IBlockEntityEvent event);

    IPuzzleZone getOffsetBlockPos(Pool<IPuzzleBlockPosition> pool, IPuzzleZone zone, int offsetX, int offsetY, int offsetZ);
    IPuzzleZone getOffsetBlockPos(IPuzzleZone zone, int offsetX, int offsetY, int offsetZ);
    IPuzzleZone getOffsetBlockPos(int offsetX, int offsetY, int offsetZ);
    IPuzzleZone getOffsetBlockPos(IPuzzleBlockPosition destBlockPos, IPuzzleZone zone, int offsetX, int offsetY, int offsetZ);
    IPuzzleZone getOffsetBlockPos(IPuzzleZone zone, Direction d);
    IPuzzleZone getOffsetBlockPos(IPuzzleBlockPosition destBlockPos, IPuzzleZone zone, Direction d);
}
