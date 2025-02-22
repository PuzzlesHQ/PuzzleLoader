package io.github.puzzle.cosmic.api.world;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import finalforeach.cosmicreach.savelib.blocks.IBlockDataFactory;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.constants.BlockStateTmpClass;
import io.github.puzzle.cosmic.api.constants.EntityTmpClass;
import io.github.puzzle.cosmic.api.constants.PlayerTmpClass;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntityUniqueId;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.annotation.Unfinished;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

import java.util.function.Consumer;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@Unfinished
@ApiGen("Zone")
public interface IPuzzleZone {

    String _getAbsolutePath();
    IPuzzleWorld _getWorld();

    IChunkManager _getChunkManager();
    IPlayerManager _getPlayerManager();
    IEntityManager _getEntityManager();

    interface IChunkManager {

        void put(IPuzzleChunk chunk, boolean immediate);
        void put(IPuzzleChunk chunk);

        IPuzzleChunk get(int cx, int cy, int cz);
        IPuzzleChunk getAtBlock(int x, int y, int z);
        IPuzzleChunk getAtVector(Vector3 position);

        void remove(IPuzzleChunk chunk);

        int getNumberOfChunks();
    }

    interface IPlayerManager {
        void addPlayer(IPuzzlePlayer player);
        void removePlayer(IPuzzlePlayer player);

        void forEachPlayer(Consumer<IPuzzlePlayer> playerConsumer);

        Array<PlayerTmpClass> getPlayers();
    }

    interface IEntityManager {

        void addEntity(IPuzzleEntity entity);

        IPuzzleEntity getEntity(IPuzzleEntityUniqueId id);
        boolean hasEntity(IPuzzleEntity e);

        void removeEntity(IPuzzleEntityUniqueId id);
        void removeEntity(IPuzzleEntity e);

        void despawnEntity(IPuzzleEntity entity);
        void forEachEntity(Consumer<IPuzzleEntity> consumer);

        Array<EntityTmpClass> getAllEntities();
    }

    IPuzzleBlockState _getBlockState(Vector3 position);
    IPuzzleBlockEntity _getBlockEntity(int x, int y, int z);

    IPuzzleBlockEntity _getBlockEntity(IPuzzleChunk candidateChunk, int x, int y, int z);
    IPuzzleBlockState _getBlockState(float x, float y, float z);
    IPuzzleBlockState _getBlockState(int x, int y, int z);
    IPuzzleBlockState _getBlockState(IPuzzleBlockPosition bp);

    IPuzzleBlockState _getBlockState(IPuzzleChunk candidateChunk, int x, int y, int z);
    IPuzzleBlockState _getBlockState(IPuzzleChunk candidateChunk, IPuzzleChunk candidateChunkB, int x, int y, int z);

    short _getBlockLight(IPuzzleChunk candidateChunk, int x, int y, int z);
    int _getSkyLight(IPuzzleChunk candidateChunk, int x, int y, int z);

    void _setBlockState(IPuzzleBlockState block, int x, int y, int z);
    void _setBlockState(IPuzzleBlockState block, int x, int y, int z, IBlockDataFactory<BlockStateTmpClass> chunkDataFactory);

    void _dispose();
    boolean _calculateSpawn();
    boolean _validateSpawnPoint();

    IPuzzleChunk _createBlankChunk(int cx, int cy, int cz);
    IPuzzleChunk _createBlankChunkAtBlock(int bx, int by, int bz);
    void _update(float deltaTime);
    long _getCurrentWorldTick();

    void _setSkyId(String skyId);
    String _getSkyId();
    String _getDefaultSkyId();

    void _setWorld(IPuzzleWorld world);

    String _getStringID();
    IPuzzleIdentifier _getId();

}
