package io.github.puzzle.cosmic.impl.mixin.util;

import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Internal
@Mixin(Identifier.class)
public class IdentifierMixin implements IPuzzleIdentifier {

    @Unique
    private final transient Identifier puzzleLoader$id = IPuzzleIdentifier.as(this);

    @Override
    public String _getNamespace() {
        return puzzleLoader$id.getNamespace();
    }

    @Override
    public String _getName() {
        return puzzleLoader$id.getName();
    }
}
