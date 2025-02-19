package io.github.puzzle.cosmic.impl.mixin.entity;

import finalforeach.cosmicreach.entities.EntityUniqueId;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntityUniqueId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityUniqueId.class)
public abstract class EntityUniqueIdMixin implements IPuzzleEntityUniqueId {

    @Shadow private long time;

    @Shadow private int rand;

    @Shadow private int number;

    public long _getTime() {
        return time;
    }

    public int _getRand() {
        return rand;
    }

    public int _getNumber() {
        return number;
    }

}
