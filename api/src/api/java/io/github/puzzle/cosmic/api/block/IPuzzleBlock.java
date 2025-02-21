package io.github.puzzle.cosmic.api.block;

import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("Block")
public interface IPuzzleBlock {

    IPuzzleBlockState _getDefaultState();
    BlockStateMap _getStates();

    IPuzzleIdentifier _getIdentifier();
    String _getName();

    interface BlockStateMap {
        IPuzzleBlockState get(String key);
    }
}
