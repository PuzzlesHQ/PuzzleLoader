package com.github.puzzle.game.aprilfoolsupdate;

import finalforeach.cosmicreach.blockentities.BlockEntity;
import finalforeach.cosmicreach.blockentities.BlockEntityCreator;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.world.Zone;

public class ForeshadowingBlockEntity extends BlockEntity {

    public static void register() {
        BlockEntityCreator.registerBlockEntityCreator("base:foreshadowing", ((blockState, zone, x, y, z) -> new ForeshadowingBlockEntity(zone, x, y, z)));
    }

    public ForeshadowingBlockEntity() {
    }

    public ForeshadowingBlockEntity(Zone zone, int globalX, int globalY, int globalZ) {
        this();
        this.setZone(zone);
        this.setGlobalPosition(globalX, globalY, globalZ);
        this.loaded = true;
    }

    @Override
    public void onInteract(Player player, Zone zone) {
        super.onInteract(player, zone);

        player.getEntity().forceHit(1);
    }

    @Override
    public String getBlockEntityId() {
        return "base:foreshadowing";
    }
}
