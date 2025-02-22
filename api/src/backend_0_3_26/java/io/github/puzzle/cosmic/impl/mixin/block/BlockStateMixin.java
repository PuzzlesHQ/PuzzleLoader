package io.github.puzzle.cosmic.impl.mixin.block;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Internal
@Mixin(BlockState.class)
public abstract class BlockStateMixin implements IPuzzleBlockState {

    @Shadow public String[] tags;
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
    public void _setTags(String[] strings) {
        puzzleLoader$state.tags = strings;
    }

    @Override
    public void _addTags(String... strings) {
        String[] tags = new String[puzzleLoader$state.tags.length + strings.length];
        System.arraycopy(puzzleLoader$state.tags, 0, tags, 0, puzzleLoader$state.tags.length);
        System.arraycopy(strings, 0, tags, puzzleLoader$state.tags.length - 1, strings.length);
        puzzleLoader$state.tags = tags;
    }

    @Override
    public void _removeTags(String... strings) {
        Set<String> tags = new HashSet<>();
        for (String tag : puzzleLoader$state.tags) {
            for (String bTag : strings) {
                if (!tag.equals(bTag)) tags.add(tag);
            }
        }
        puzzleLoader$state.tags = tags.toArray(new String[0]);
    }
}
