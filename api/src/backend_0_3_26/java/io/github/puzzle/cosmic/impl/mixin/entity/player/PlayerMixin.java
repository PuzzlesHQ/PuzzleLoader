package io.github.puzzle.cosmic.impl.mixin.entity.player;

import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.entities.player.Player;
import io.github.puzzle.cosmic.api.account.IPuzzleAccount;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.item.IPuzzleItemStack;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleWorld;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Internal
@Mixin(Player.class)
public abstract class PlayerMixin implements IPuzzlePlayer {

    @Unique
    private final transient Player puzzleLoader$player = IPuzzlePlayer.as(this);

    @Override
    public IPuzzleEntity _getEntity() {
        return IPuzzleEntity.as(puzzleLoader$player.getEntity());
    }

    @Override
    public void _proneCheck(IPuzzleZone iPuzzleZone) {
        puzzleLoader$player.proneCheck(iPuzzleZone.as());
    }

    @Override
    public void _crouchCheck(IPuzzleZone iPuzzleZone) {
        puzzleLoader$player.crouchCheck(iPuzzleZone.as());
    }

    @Override
    public void _respawn(IPuzzleWorld iPuzzleWorld) {
        puzzleLoader$player.respawn(iPuzzleWorld.as());
    }

    @Override
    public void _respawn(IPuzzleZone iPuzzleZone) {
        puzzleLoader$player.respawn(iPuzzleZone.as());
    }

    @Override
    public void _setPosition(float x, float y, float z) {
        puzzleLoader$player.getEntity().setPosition(x, y, z);
    }

    @Override
    public IPuzzleZone _getZone() {
        return IPuzzleZone.as(GameSingletons.world.getZoneCreateIfNull(puzzleLoader$player.zoneId));
    }

    @Override
    public IPuzzleChunk _getChunk(IPuzzleWorld world) {
        return IPuzzleChunk.as(puzzleLoader$player.getChunk(world.as()));
    }

    @Override
    public short _getBlockLight(IPuzzleWorld iPuzzleWorld) {
        return puzzleLoader$player.getBlockLight(iPuzzleWorld.as());
    }

    @Override
    public int _getSkyLight(IPuzzleWorld iPuzzleWorld) {
        return puzzleLoader$player.getSkyLight(iPuzzleWorld.as());
    }

    @Override
    public void _spawnDroppedItem(IPuzzleWorld iPuzzleWorld, IPuzzleItemStack iPuzzleItemStack) {
        puzzleLoader$player.spawnDroppedItem(iPuzzleWorld.as(), iPuzzleItemStack.as());
    }

    @Override
    public boolean _isLoading() {
        return puzzleLoader$player.loading;
    }

    @Override
    public IPuzzleAccount _getAccount() {
        return IPuzzleAccount.as(GameSingletons.getAccountFromPlayer(as()));
    }

}
