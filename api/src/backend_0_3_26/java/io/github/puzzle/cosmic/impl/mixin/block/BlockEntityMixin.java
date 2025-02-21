package io.github.puzzle.cosmic.impl.mixin.block;

import finalforeach.cosmicreach.blockentities.BlockEntity;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.event.IBlockEntityEvent;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.impl.event.BlockEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements IPuzzleBlockEntity {

    @Unique
    private final transient BlockEntity puzzleLoader$entity = IPuzzleBlockEntity.as(this);

    @Override
    public int _getGlobalX() {
        return puzzleLoader$entity.getGlobalX();
    }

    @Override
    public int _getGlobalY() {
        return puzzleLoader$entity.getGlobalY();
    }

    @Override
    public int _getGlobalZ() {
        return puzzleLoader$entity.getGlobalZ();
    }

    @Override
    public IPuzzleBlockPosition _getBlockPosition() {
        return IPuzzleBlockPosition.as(new BlockPosition(_getChunk().as(), _getLocalX(), _getLocalY(), _getLocalZ()));
    }

    @Override
    public IPuzzleZone _getZone() {
        return IPuzzleZone.as(puzzleLoader$entity.getZone());
    }

    @Override
    public IPuzzleChunk _getChunk() {
        return IPuzzleChunk.as(puzzleLoader$entity.getZone().getChunkAtBlock(
                _getGlobalX(),
                _getGlobalY(),
                _getGlobalZ()
        ));
    }

    @Override
    public IPuzzleIdentifier _getIdentifier() {
        return (IPuzzleIdentifier) Identifier.of(puzzleLoader$entity.getBlockEntityId());
    }

    @Override
    public void _onCreate(IPuzzleBlockState iPuzzleBlockState) {
        puzzleLoader$entity.onCreate(iPuzzleBlockState.as());
    }

    @Override
    public void _onLoad() {
        puzzleLoader$entity.onLoad();
    }

    @Override
    public void _onUnload() {
        puzzleLoader$entity.onUnload();
    }

    @Override
    public void _setTicking(boolean b) {
        puzzleLoader$entity.setTicking(b);
    }

    @Override
    public void _onTick() {
        puzzleLoader$entity.onTick();
    }

    @Override
    public boolean _isTicking() {
        return puzzleLoader$entity.isTicking();
    }

    @Override
    public void _onInteract(IPuzzlePlayer iPuzzlePlayer, IPuzzleZone iPuzzleZone) {
        puzzleLoader$entity.onInteract(iPuzzlePlayer.as(), iPuzzleZone.as());
    }

    @Override
    public void _onSetBlockState(IPuzzleBlockState iPuzzleBlockState) {
        puzzleLoader$entity.onSetBlockState(iPuzzleBlockState.as());
    }

    @Override
    public void _setZone(IPuzzleZone iPuzzleZone) {
        puzzleLoader$entity.setZone(iPuzzleZone.as());
    }

    @Override
    public IPuzzleBlockState _getBlockState() {
        return IPuzzleBlockState.as(puzzleLoader$entity.getBlockState());
    }

    @Override
    public void _onNeighborUpdate(IBlockEntityEvent iBlockEntityEvent) {
        // Insert code here
    }

    @Override
    public void _updateNeighbors(IBlockEntityEvent iBlockEntityEvent) {
        _getBlockPosition()._updateNeighboringBlockEntities(iBlockEntityEvent);
    }

    @Override
    public void _updateNeighbors() {
        _getBlockPosition()._updateNeighboringBlockEntities(BlockEntityEvent.of(this));
    }

    @Override
    public BlockEntity as() {
        return puzzleLoader$entity;
    }
}
