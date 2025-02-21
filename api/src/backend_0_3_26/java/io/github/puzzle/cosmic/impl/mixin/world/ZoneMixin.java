package io.github.puzzle.cosmic.impl.mixin.world;

import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.world.Zone;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Internal
@Mixin(Zone.class)
public class ZoneMixin implements IPuzzleZone {

    @Unique
    private final transient Zone puzzleLoader$zone = IPuzzleZone.as(this);

    @Override
    public IPuzzleIdentifier getId() {
        return IPuzzleIdentifier.as(Identifier.of(puzzleLoader$zone.zoneId));
    }
}
