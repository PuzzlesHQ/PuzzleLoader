package com.github.puzzle.game.items.puzzle;

import com.github.puzzle.game.items.IModItem;
import com.github.puzzle.game.items.impl.AbstractItem;
import com.github.puzzle.game.util.BlockUtil;
import finalforeach.cosmicreach.blockentities.BlockEntity;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.items.ItemBlock;
import finalforeach.cosmicreach.items.ItemSlot;
import finalforeach.cosmicreach.util.Identifier;

import static com.github.puzzle.core.Constants.MOD_ID;

public class BlockWrench extends AbstractItem {

    public BlockWrench() {
        super(Identifier.of(MOD_ID, "block_wrench"));
        manifest.addTag(IModItem.MODEL_ID_PRESET.createTag(IModItem.MODEL_2_5D_ITEM));
        manifest.addTag(IModItem.TEXTURE_LOCATION_PRESET.createTag(Identifier.of(MOD_ID, "block_wrench.png")));
    }

    @Override
    public void use(ItemSlot slot, Player player, BlockPosition targetPlaceBlockPos, BlockPosition targetBreakBlockPos) {
        if (targetBreakBlockPos == null) return;
        BlockState state = targetBreakBlockPos.getBlockState();
        if (state == null) return;
        BlockUtil.setBlockAt(targetBreakBlockPos.getZone(), ((ItemBlock) state.getItem().getNextSwapGroupItem()).getBlockState(), targetBreakBlockPos);
    }

    @Override
    public boolean isTool() {
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public String getName() {
        return "State Wrench";
    }

    @Override
    public boolean canBreakBlockWith(BlockState blockState) {
        return false;
    }

    @Override
    public boolean canInteractWithBlock(BlockState blockState) {
        return false;
    }

    @Override
    public boolean canInteractWithBlockEntity(BlockEntity blockEntity) {
        return false;
    }
}
