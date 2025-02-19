package io.github.puzzle.cosmic.impl.mixin.entity.player;

import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.world.Chunk;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.Zone;
import io.github.puzzle.cosmic.api.account.IPuzzleAccount;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.item.IPuzzleItemStack;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.api.world.IPuzzleWorld;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerMixin implements IPuzzlePlayer {

    @Shadow private Entity entity;

    @Shadow public abstract void proneCheck(Zone zone);

    @Shadow public abstract void crouchCheck(Zone zone);

    @Shadow public abstract void respawn(Zone zone);

    @Shadow public abstract void respawn(World world);

    @Shadow public String zoneId;

    @Shadow public abstract Chunk getChunk(World world);

    @Shadow public abstract short getBlockLight(World world);

    @Shadow public abstract int getSkyLight(World world);

    @Shadow public abstract void spawnDroppedItem(World world, ItemStack itemStack);

    @Shadow public abstract void setZone(String zoneId);

    @Shadow public transient boolean loading;

    @Override
    public IPuzzleEntity _getEntity() {
        return IPuzzleEntity.as(entity);
    }

    @Override
    public void _proneCheck(IPuzzleZone iPuzzleZone) {
        proneCheck(iPuzzleZone.as());
    }

    @Override
    public void _crouchCheck(IPuzzleZone iPuzzleZone) {
        crouchCheck(iPuzzleZone.as());
    }

    @Override
    public void _respawn(IPuzzleWorld iPuzzleWorld) {
        respawn(iPuzzleWorld.as());
    }

    @Override
    public void _respawn(IPuzzleZone iPuzzleZone) {
        respawn(iPuzzleZone.as());
    }

    @Override
    public void _setPosition(float x, float y, float z) {
        entity.setPosition(x, y, z);
    }

    @Override
    public IPuzzleZone _getZone() {
        return IPuzzleZone.as(GameSingletons.world.getZoneCreateIfNull(zoneId));
    }

    @Override
    public IPuzzleChunk _getChunk(IPuzzleWorld world) {
        return IPuzzleChunk.as(getChunk(world.as()));
    }

    @Override
    public short _getBlockLight(IPuzzleWorld iPuzzleWorld) {
        return getBlockLight(iPuzzleWorld.as());
    }

    @Override
    public int _getSkyLight(IPuzzleWorld iPuzzleWorld) {
        return getSkyLight(iPuzzleWorld.as());
    }

    @Override
    public void _spawnDroppedItem(IPuzzleWorld iPuzzleWorld, IPuzzleItemStack iPuzzleItemStack) {
        spawnDroppedItem(iPuzzleWorld.as(), iPuzzleItemStack.as());
    }

    @Override
    public boolean _isLoading() {
        return loading;
    }

    @Override
    public IPuzzleAccount _getAccount() {
        return IPuzzleAccount.as(GameSingletons.getAccountFromPlayer(as()));
    }

}
