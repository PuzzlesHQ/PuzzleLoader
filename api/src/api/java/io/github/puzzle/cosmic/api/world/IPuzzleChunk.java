package io.github.puzzle.cosmic.api.world;

import finalforeach.cosmicreach.savelib.ISavedChunk;
import finalforeach.cosmicreach.savelib.blockdata.IBlockData;
import finalforeach.cosmicreach.savelib.blocks.IBlockDataFactory;
import finalforeach.cosmicreach.savelib.lightdata.skylight.ISkylightData;
import io.github.puzzle.cosmic.api.block.IPuzzleBlock;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.constants.IChunkMeshGroupTmp;
import io.github.puzzle.cosmic.util.annotation.Unfinished;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

import java.util.function.Consumer;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("Chunk")
public interface IPuzzleChunk {

    void _compact();
    void _dispose();

    void _fill(IPuzzleBlockState state);
    void _fillLayer(IPuzzleBlockState state, int localY);

    short _getBlockLight(int localX, int localY, int localZ);
    int _getSkyLight(int localX, int localY, int localZ);
    IPuzzleBlockState _getBlockState(int localX, int localY, int localZ);

    void _setBlockLight(int r, int g, int b, int localX, int localY, int localZ);
    void _setBlockState(IPuzzleBlockState state, int x, int y, int z);
    void _setSkyLight(int skyLight, int localX, int localY, int localZ);

    boolean _isEntirelyOpaque();
    boolean _isEntirelyOneBlockSelfCulling();
    int _getMaxNonEmptyBlockIdxYXZ();

    IMeshingController _getMeshingController();
    IBlockEntityController _getBlockEntityController();

    interface IMeshingController {

        void flagForRemeshing(boolean immediate);
        void flagHorizontalTouchingChunksForRemeshing(IPuzzleZone zone, boolean immediate);
        void flagTouchingChunksForRemeshing(IPuzzleZone zone, boolean immediate);
        void flagTouchingChunksForRemeshing(IPuzzleZone zone, int localX, int localY, int localZ, boolean immediate);

        void setMeshGroup(IChunkMeshGroupTmp<?> meshGroup);
        IChunkMeshGroupTmp<?> getMeshGroup();
    }

    interface IBlockEntityController {

        IPuzzleBlockEntity get(int localX, int localY, int localZ);
        IPuzzleBlockEntity put(IPuzzleBlockState state, int localX, int localY, int localZ);
        IPuzzleBlockEntity put(IPuzzleBlockState state, IPuzzleBlockEntity entity, int localX, int localY, int localZ);

        void foreach(Consumer<IPuzzleBlockEntity> entityConsumer);

        int getCount();
        boolean isEmpty();
    }

    int _getBlockX();
    int _getBlockY();
    int _getBlockZ();

    int _getChunkX();
    int _getChunkY();
    int _getChunkZ();

}
