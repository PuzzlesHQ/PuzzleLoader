package io.github.puzzle.cosmic.impl.mixin.block;

import finalforeach.cosmicreach.GameTag;
import finalforeach.cosmicreach.GameTagList;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.block.IPuzzleBlock;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.item.IPuzzleItem;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;

@Internal
@Mixin(BlockState.class)
public abstract class BlockStateMixin implements IPuzzleBlockState {

    @Unique
    private final transient BlockState puzzleLoader$state = IPuzzleBlockState.as(this);

    @Override
    public IPuzzleBlock _getBlock() {
        return (IPuzzleBlock) puzzleLoader$state.getBlock();
    }

    @Override
    public IPuzzleItem _getAsItem() {
        return (IPuzzleItem) puzzleLoader$state.getItem();
    }

    @Override
    public IPuzzleIdentifier _getBlockID() {
        return (IPuzzleIdentifier) Identifier.of(puzzleLoader$state.getBlockId());
    }

    @Override
    public String _getSaveKey() {
        return puzzleLoader$state.getSaveKey();
    }

    @Override
    public void _setTags(GameTagList list) {
        puzzleLoader$state.tags = list;
    }

    @Override
    public void _addTags(GameTag... tagz) {
        for (GameTag t : tagz) puzzleLoader$state.tags.add(t);
    }

    @Override
    public void _removeTags(GameTag... tagz) {
        for (GameTag t : tagz) puzzleLoader$state.tags.remove(t);
    }

    @Override
    public void _addTags(Collection<GameTag> collection) {
        puzzleLoader$state.tags.addAll(collection);
    }

    @SafeVarargs
    @Override
    public final void _removeTags(Collection<GameTag>... collection) {
        puzzleLoader$state.tags.removeAll((Collection<?>) (Object) collection);
    }

}
