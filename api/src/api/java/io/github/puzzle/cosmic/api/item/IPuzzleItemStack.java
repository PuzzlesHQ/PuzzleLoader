package io.github.puzzle.cosmic.api.item;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockPosition;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockState;
import io.github.puzzle.cosmic.api.data.point.IDataPointManifest;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("ItemStack")
public interface IPuzzleItemStack extends ICRBinSerializable {

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
    void _setPointManifest(IDataPointManifest manifest);

    // ICRBinSerializable.java methods

    @Override
    void read(CRBinDeserializer crBinDeserializer);

    @Override
    void write(CRBinSerializer crBinSerializer);
}
