package io.github.puzzle.cosmic.api.block;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.api.constants.Direction;
import io.github.puzzle.cosmic.api.data.point.IDataPointManifest;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.event.IBlockUpdateEvent;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;


/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("BlockEntity")
public interface IPuzzleBlockEntity extends ICRBinSerializable {

    int _getGlobalX();
    int _getGlobalY();
    int _getGlobalZ();

    default int _getLocalX() {
        int chunkX = Math.floorDiv(_getGlobalX(), 16);
        return _getGlobalX() - chunkX * 16;
    }

    default int _getLocalY() {
        int chunkY = Math.floorDiv(_getGlobalY(), 16);
        return _getGlobalY() - chunkY * 16;
    }

    default int _getLocalZ() {
        int chunkZ = Math.floorDiv(_getGlobalZ(), 16);
        return _getGlobalZ() - chunkZ * 16;
    }

    IPuzzleBlockPosition _getBlockPosition();

    IPuzzleZone _getZone();
    IPuzzleChunk _getChunk();

    IPuzzleIdentifier _getIdentifier();

    void _onCreate(IPuzzleBlockState state);

    void _onLoad();
    void _onUnload();

    void _setTicking(boolean ticking);
    void _onTick();

    boolean _isTicking();

    void _onInteract(IPuzzlePlayer player, IPuzzleZone zone);
    void _onSetBlockState(IPuzzleBlockState state);
    void _setZone(IPuzzleZone zone);

    IPuzzleBlockState _getBlockState();

    void _updateNeighbors(IBlockUpdateEvent event);
    void _updateNeighborInDirection(Direction direction, IBlockUpdateEvent event);

    void _onNeighborUpdate(IBlockUpdateEvent event);

    IDataPointManifest _getPointManifest();
    void _setPointManifest(IDataPointManifest manifest);

    // ICRBinSerializable.java methods

    @Override
    void read(CRBinDeserializer deserial);

    @Override
    void write(CRBinSerializer serial);

}
