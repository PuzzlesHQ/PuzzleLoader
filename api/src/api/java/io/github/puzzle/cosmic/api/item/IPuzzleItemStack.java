package io.github.puzzle.cosmic.api.item;

import com.badlogic.gdx.math.Vector3;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.data.IDataPointManifest;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.ApiDeclaration;

@ApiDeclaration(api = IPuzzleItemStack.class, impl = "ItemStack")
public interface IPuzzleItemStack {

    IPuzzleItemStack _copy();
    IPuzzleItem _getItem();
    void _setItem(IPuzzleItem item);
    void _cycleSwapGroupItem();

    IPuzzleEntity _spawnItemEntityAt(IPuzzleZone zone, Vector3 pos);
    void _spawnItemEntityAt(IPuzzleBlockPosition position);

    boolean _useItem(IPuzzleItemSlot slot, IPuzzlePlayer player, IPuzzleBlockPosition position);

    int _getDurability();
    int _getMaxDurability();
    boolean _hasDurability();

    void _damage(int damage);
    boolean _isBroken();

    boolean _canTargetBlockForBreaking(IPuzzleBlockState state);
    boolean _isEffectiveBreaking(IPuzzleBlockState state);
    float _getEffectiveBreakingSpeed();

    IPuzzleItemStack _setAmount(int amount);

    String _getName();

    IDataPointManifest _getPointManifest();
}
