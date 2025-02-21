package io.github.puzzle.cosmic.api.entity.player;

import com.badlogic.gdx.math.Vector3;
import io.github.puzzle.cosmic.api.account.IPuzzleAccount;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.item.IPuzzleItemStack;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleWorld;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("Player")
public interface IPuzzlePlayer {

    IPuzzleEntity _getEntity();

    void _proneCheck(IPuzzleZone zone);
    void _crouchCheck(IPuzzleZone zone);

    void _respawn(IPuzzleWorld world);
    void _respawn(IPuzzleZone zone);

    void _setPosition(float x, float y, float z);

    IPuzzleZone _getZone();
    IPuzzleChunk _getChunk(IPuzzleWorld world);

    short _getBlockLight(IPuzzleWorld world);
    int _getSkyLight(IPuzzleWorld world);

    void _spawnDroppedItem(IPuzzleWorld world, IPuzzleItemStack itemStack);

    Vector3 _getPosition();

    boolean _isLoading();

    default void setZone(IPuzzleZone zone) {
        setZone(zone.getId());
    }

    default void setZone(IPuzzleIdentifier zoneId) {
        setZone(zoneId.asString());
    }

    void setZone(String zoneId);

    boolean _isDead();

    IPuzzleAccount _getAccount();
    String _getUsername();

}
