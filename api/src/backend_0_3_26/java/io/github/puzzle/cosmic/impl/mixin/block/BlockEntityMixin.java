package io.github.puzzle.cosmic.impl.mixin.block;

import finalforeach.cosmicreach.blockentities.BlockEntity;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.constants.Direction;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.data.point.IDataPointManifest;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.event.IBlockUpdateEvent;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.impl.data.point.DataPointManifest;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Internal
@Mixin(BlockEntity.class)
public class BlockEntityMixin implements IPuzzleBlockEntity {

    @Unique
    private final transient BlockEntity puzzleLoader$entity = IPuzzleBlockEntity.as(this);

    @Unique
    private transient IDataPointManifest puzzleLoader$manifest = new DataPointManifest();

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
    public void _onNeighborUpdate(IBlockUpdateEvent iBlockUpdateEvent) {
        // Implemented to prevent crash, can be overridden.
    }

    @Override
    public void _updateNeighbors(IBlockUpdateEvent event) {
        _getBlockPosition()._updateNeighbors(event);
    }

    @Override
    public void _updateNeighborInDirection(Direction direction, IBlockUpdateEvent event) {
        _getBlockPosition()._updateNeighborInDirection(event, direction);
    }

    @Override
    public BlockEntity as() {
        return puzzleLoader$entity;
    }

    @Inject(method = "read", at = @At("TAIL"), remap = false)
    private void write(CRBinDeserializer crbd, CallbackInfo ci) {
        IDataPointManifest manifest = crbd.readObj("point_manifest", DataPointManifest.class);
        if (manifest != null) _setPointManifest(manifest);
    }

    @Inject(method = "write", at = @At("TAIL"), remap = false)
    private void write(CRBinSerializer crbs, CallbackInfo ci) {
        crbs.writeObj("point_manifest", puzzleLoader$manifest);
    }

    @Override
    public IDataPointManifest _getPointManifest() {
        return puzzleLoader$manifest;
    }

    @Override
    public void _setPointManifest(IDataPointManifest iDataPointManifest) {
        puzzleLoader$manifest = iDataPointManifest;
    }
}
