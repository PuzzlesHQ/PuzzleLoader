package io.github.puzzle.cosmic.impl.mixin.item;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.data.point.IDataPointManifest;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.item.IPuzzleItem;
import io.github.puzzle.cosmic.api.item.IPuzzleItemSlot;
import io.github.puzzle.cosmic.api.item.IPuzzleItemStack;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.impl.data.point.DataPointManifest;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Internal
@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IPuzzleItemStack {

    @Unique
    private final transient ItemStack puzzleLoader$stack = IPuzzleItemStack.as(this);

    @Unique
    private transient IDataPointManifest puzzleLoader$manifest = new DataPointManifest();

    @Override
    public IPuzzleItemStack _copy() {
        return IPuzzleItemStack.as(puzzleLoader$stack.copy());
    }

    @Override
    public IPuzzleItem _getItem() {
        return IPuzzleItem.as(puzzleLoader$stack.getItem());
    }

    @Override
    public void _setItem(IPuzzleItem iPuzzleItem) {
        puzzleLoader$stack.setItem(iPuzzleItem.as());
    }

    @Override
    public void _cycleSwapGroupItem() {
        puzzleLoader$stack.cycleSwapGroupItem();
    }

    @Override
    public IPuzzleEntity _spawnItemEntityAt(IPuzzleZone iPuzzleZone, Vector3 vector3) {
        return IPuzzleEntity.as(puzzleLoader$stack.spawnItemEntityAt(iPuzzleZone.as(), vector3));
    }

    @Override
    public void _spawnItemEntityAt(IPuzzleBlockPosition iPuzzleBlockPosition) {
        puzzleLoader$stack.spawnItemEntityAt(iPuzzleBlockPosition.as());
    }

    @Override
    public boolean _useItem(IPuzzleItemSlot iPuzzleItemSlot, IPuzzlePlayer iPuzzlePlayer, IPuzzleBlockPosition iPuzzleBlockPosition) {
        return puzzleLoader$stack.useItem(iPuzzleItemSlot.as(), iPuzzlePlayer.as(), iPuzzleBlockPosition.as());
    }

    @Override
    public int _getDurability() {
        return puzzleLoader$stack.getDurability();
    }

    @Override
    public int _getMaxDurability() {
        return puzzleLoader$stack.getMaxDurability();
    }

    @Override
    public boolean _hasDurability() {
        return puzzleLoader$stack.hasDurability();
    }

    @Override
    public void _damage(int i) {
        puzzleLoader$stack.damage(i);
    }

    @Override
    public boolean _isBroken() {
        return puzzleLoader$stack.isBroken();
    }

    @Override
    public boolean _canTargetBlockForBreaking(IPuzzleBlockState iPuzzleBlockState) {
        return puzzleLoader$stack.canTargetBlockForBreaking(iPuzzleBlockState.as());
    }

    @Override
    public boolean _isEffectiveBreaking(IPuzzleBlockState iPuzzleBlockState) {
        return puzzleLoader$stack.isEffectiveBreaking(iPuzzleBlockState.as());
    }

    @Override
    public float _getEffectiveBreakingSpeed() {
        return puzzleLoader$stack.getEffectiveBreakingSpeed();
    }

    @Override
    public IPuzzleItemStack _setAmount(int i) {
        return IPuzzleItemStack.as(puzzleLoader$stack.setAmount(i));
    }

    @Override
    public String _getName() {
        return puzzleLoader$stack.getName();
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
