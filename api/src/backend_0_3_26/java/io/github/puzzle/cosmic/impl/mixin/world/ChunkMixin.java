package io.github.puzzle.cosmic.impl.mixin.world;

import finalforeach.cosmicreach.world.Chunk;
import io.github.puzzle.cosmic.api.world.IPuzzleChunk;
import io.github.puzzle.cosmic.util.Internal;
import org.spongepowered.asm.mixin.Mixin;

@Internal
@Mixin(Chunk.class)
public class ChunkMixin implements IPuzzleChunk {

}
