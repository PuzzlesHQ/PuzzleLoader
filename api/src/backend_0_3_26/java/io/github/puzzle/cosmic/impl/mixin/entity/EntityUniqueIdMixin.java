package io.github.puzzle.cosmic.impl.mixin.entity;

import finalforeach.cosmicreach.entities.EntityUniqueId;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntityUniqueId;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Internal
@Mixin(EntityUniqueId.class)
public abstract class EntityUniqueIdMixin implements IPuzzleEntityUniqueId {

    @Unique
    private final transient EntityUniqueId puzzleLoader$uid = IPuzzleEntityUniqueId.as(this);

    public long _getTime() {
        return puzzleLoader$uid.getTime();
    }

    public int _getRand() {
        return puzzleLoader$uid.getRand();
    }

    public int _getNumber() {
        return puzzleLoader$uid.getNumber();
    }

}
