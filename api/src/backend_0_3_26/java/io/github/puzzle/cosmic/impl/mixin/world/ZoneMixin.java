package io.github.puzzle.cosmic.impl.mixin.world;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.savelib.blocks.IBlockDataFactory;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.world.Zone;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntityUniqueId;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleWorld;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Internal
@Mixin(Zone.class)
public class ZoneMixin implements IPuzzleZone {

    @Unique
    private final transient Zone puzzleLoader$zone = IPuzzleZone.as(this);

    @Override
    public IChunkManager _getChunkManager() {
        return puzzleLoader$chunkManager;
    }

    @Override
    public IEntityManager _getEntityManager() {
        return puzzleLoader$entityManager;
    }

    @Override
    public IPlayerManager _getPlayerManager() {
        return puzzleLoader$playerManager;
    }

    @Override
    public String _getAbsolutePath() {
        return puzzleLoader$zone.getFullSaveFolder();
    }

    @Override
    public IPuzzleWorld _getWorld() {
        return IPuzzleWorld.as(puzzleLoader$zone.getWorld());
    }

    @Unique
    private final transient IChunkManager puzzleLoader$chunkManager = new IChunkManager() {

        @Override
        public void put(IPuzzleChunk iPuzzleChunk, boolean b) {
            puzzleLoader$zone.addOrReplaceChunk(iPuzzleChunk.as(), b);
        }

        @Override
        public void put(IPuzzleChunk iPuzzleChunk) {
            puzzleLoader$zone.addChunk(iPuzzleChunk.as());
        }

        @Override
        public IPuzzleChunk get(int i, int i1, int i2) {
            return IPuzzleChunk.as(puzzleLoader$zone.getChunkAtChunkCoords(i, i1, i2));
        }

        @Override
        public IPuzzleChunk getAtBlock(int i, int i1, int i2) {
            return IPuzzleChunk.as(puzzleLoader$zone.getChunkAtBlock(i, i1, i2));
        }

        @Override
        public IPuzzleChunk getAtVector(Vector3 vector3) {
            return IPuzzleChunk.as(puzzleLoader$zone.getChunkAtPosition(vector3));
        }

        @Override
        public void remove(IPuzzleChunk iPuzzleChunk) {
            puzzleLoader$zone.removeChunk(iPuzzleChunk.as());
        }

        @Override
        public int getNumberOfChunks() {
            return puzzleLoader$zone.getNumberOfChunks();
        }
    };

    @Unique
    private final transient IEntityManager puzzleLoader$entityManager = new IEntityManager() {

        @Override
        public void addEntity(IPuzzleEntity iPuzzleEntity) {
            puzzleLoader$zone.addEntity(iPuzzleEntity.as());
        }

        @Override
        public IPuzzleEntity getEntity(IPuzzleEntityUniqueId iPuzzleEntityUniqueId) {
            return IPuzzleEntity.as(puzzleLoader$zone.getEntity(iPuzzleEntityUniqueId.as()));
        }

        @Override
        public boolean hasEntity(IPuzzleEntity iPuzzleEntity) {
            return puzzleLoader$zone.hasEntity(iPuzzleEntity.as());
        }

        @Override
        public void removeEntity(IPuzzleEntityUniqueId iPuzzleEntityUniqueId) {
            puzzleLoader$zone.removeEntity(iPuzzleEntityUniqueId.as());
        }

        @Override
        public void removeEntity(IPuzzleEntity iPuzzleEntity) {
            puzzleLoader$zone.removeEntity(iPuzzleEntity.as());
        }

        @Override
        public void despawnEntity(IPuzzleEntity iPuzzleEntity) {
            puzzleLoader$zone.despawnEntity(iPuzzleEntity.as());
        }

        @Override
        public void forEachEntity(Consumer<IPuzzleEntity> consumer) {
            puzzleLoader$zone.forEachEntity((c) -> consumer.accept(IPuzzleEntity.as(c)));
        }

        @Override
        public Array<Entity> getAllEntities() {
            return puzzleLoader$zone.getAllEntities();
        }
    };

    @Unique
    private final transient IPlayerManager puzzleLoader$playerManager = new IPlayerManager() {

        @Override
        public void addPlayer(IPuzzlePlayer iPuzzlePlayer) {
            puzzleLoader$zone.addPlayer(iPuzzlePlayer.as());
        }

        @Override
        public void removePlayer(IPuzzlePlayer iPuzzlePlayer) {
            puzzleLoader$zone.removePlayer(iPuzzlePlayer.as());
        }

        @Override
        public void forEachPlayer(Consumer<IPuzzlePlayer> consumer) {
            puzzleLoader$zone.forEachPlayer((c) -> consumer.accept(IPuzzlePlayer.as(c)));
        }

        @Override
        public Array<Player> getPlayers() {
            return puzzleLoader$zone.getPlayers();
        }
    };

    @Override
    public IPuzzleBlockState _getBlockState(IPuzzleBlockPosition iPuzzleBlockPosition) {
        return iPuzzleBlockPosition._getBlockState();
    }

    public IPuzzleBlockState _getBlockState(Vector3 position) {
        return IPuzzleBlockState.as(puzzleLoader$zone.getBlockState(position));
    }

    public IPuzzleBlockEntity _getBlockEntity(int x, int y, int z) {
        return IPuzzleBlockEntity.as(puzzleLoader$zone.getBlockEntity(x, y, z));
    }

    public IPuzzleBlockEntity _getBlockEntity(IPuzzleChunk candidateChunk, int x, int y, int z) {
        return IPuzzleBlockEntity.as(puzzleLoader$zone.getBlockEntity(candidateChunk.as(), x, y, z));
    }

    public IPuzzleBlockState _getBlockState(float x, float y, float z) {
        return IPuzzleBlockState.as(puzzleLoader$zone.getBlockState(x, y, z));
    }

    public IPuzzleBlockState _getBlockState(int x, int y, int z) {
        return IPuzzleBlockState.as(puzzleLoader$zone.getBlockState(x, y, z));
    }

    public IPuzzleBlockState _getBlockState(IPuzzleChunk candidateChunk, int x, int y, int z) {
        return IPuzzleBlockState.as(puzzleLoader$zone.getBlockState(candidateChunk.as(), x, y, z));
    }

    public IPuzzleBlockState _getBlockState(IPuzzleChunk candidateChunk, IPuzzleChunk candidateChunkB, int x, int y, int z) {
        return IPuzzleBlockState.as(puzzleLoader$zone.getBlockState(candidateChunk.as(), candidateChunkB.as(), x, y, z));
    }

    @Override
    public short _getBlockLight(IPuzzleChunk iPuzzleChunk, int i, int i1, int i2) {
        return puzzleLoader$zone.getBlockLight(iPuzzleChunk.as(), i, i1, i2);
    }

    @Override
    public int _getSkyLight(IPuzzleChunk iPuzzleChunk, int i, int i1, int i2) {
        return puzzleLoader$zone.getSkyLight(iPuzzleChunk.as(), i, i1, i2);
    }

    @Override
    public void _setBlockState(IPuzzleBlockState iPuzzleBlockState, int i, int i1, int i2, IBlockDataFactory<BlockState> iBlockDataFactory) {
        puzzleLoader$zone.setBlockState(iPuzzleBlockState.as(), i, i1, i2, iBlockDataFactory);
    }

    @Override
    public void _setBlockState(IPuzzleBlockState iPuzzleBlockState, int i, int i1, int i2) {
        puzzleLoader$zone.setBlockState(iPuzzleBlockState.as(), i, i1, i2);
    }

    @Override
    public void _dispose() {
        puzzleLoader$zone.dispose();
    }

    @Override
    public boolean _calculateSpawn() {
        return puzzleLoader$zone.calculateSpawn();
    }

    @Override
    public boolean _validateSpawnPoint() {
        return puzzleLoader$zone.validateSpawnPoint();
    }

    @Override
    public IPuzzleChunk _createBlankChunk(int i, int i1, int i2) {
        return IPuzzleChunk.as(puzzleLoader$zone.createBlankChunk(i, i1, i2));
    }

    @Override
    public IPuzzleChunk _createBlankChunkAtBlock(int i, int i1, int i2) {
        return IPuzzleChunk.as(puzzleLoader$zone.createBlankChunkAtBlock(i, i1, i2));
    }

    @Override
    public void _update(float v) {
        puzzleLoader$zone.update(v);
    }

    @Override
    public long _getCurrentWorldTick() {
        return puzzleLoader$zone.getCurrentWorldTick();
    }

    @Override
    public void _setSkyId(String s) {
        puzzleLoader$zone.setSkyId(s);
    }

    @Override
    public String _getSkyId() {
        return puzzleLoader$zone.getSkyId();
    }

    @Override
    public String _getDefaultSkyId() {
        return puzzleLoader$zone.getDefaultSkyId();
    }

    @Override
    public void _setWorld(IPuzzleWorld iPuzzleWorld) {
        puzzleLoader$zone.setWorld(iPuzzleWorld.as());
    }

    @Override
    public String _getStringID() {
        return puzzleLoader$zone.zoneId;
    }

    @Override
    public IPuzzleIdentifier _getId() {
        return IPuzzleIdentifier.as(Identifier.of(puzzleLoader$zone.zoneId));
    }
}
