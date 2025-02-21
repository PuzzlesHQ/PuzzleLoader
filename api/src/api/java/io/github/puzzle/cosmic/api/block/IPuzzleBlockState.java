package io.github.puzzle.cosmic.api.block;

import finalforeach.cosmicreach.savelib.blocks.IBlockState;
import io.github.puzzle.cosmic.api.item.IPuzzleItem;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("BlockState")
public interface IPuzzleBlockState extends IBlockState {

    IPuzzleBlock _getBlock();
    IPuzzleItem _getAsItem();

    IPuzzleIdentifier _getBlockID();
    String _getSaveKey();

}
