package io.github.puzzle.cosmic.api.block;

import finalforeach.cosmicreach.savelib.blocks.IBlockState;
import io.github.puzzle.cosmic.api.constants.GameTagListTmpClass;
import io.github.puzzle.cosmic.api.constants.GameTagTmpClass;
import io.github.puzzle.cosmic.api.item.IPuzzleItem;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

import java.util.Collection;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("BlockState")
public interface IPuzzleBlockState extends IBlockState {

    IPuzzleBlock _getBlock();
    IPuzzleItem _getAsItem();

    void _setTags(GameTagListTmpClass tags);
    void _addTags(GameTagTmpClass... tags);
    void _addTags(Collection<GameTagTmpClass> tags);
    void _removeTags(GameTagTmpClass... tags);
    void _removeTags(Collection<GameTagTmpClass>... tags);

    IPuzzleIdentifier _getBlockID();
    String _getSaveKey();

}
