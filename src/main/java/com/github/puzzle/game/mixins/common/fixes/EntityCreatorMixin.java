package com.github.puzzle.game.mixins.common.fixes;

import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.entity.EntityType;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.entities.EntityCreator;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Function;

@Mixin(EntityCreator.class)
public class EntityCreatorMixin {

    /**
     * @author Mr_Zombii
     * @reason Force game to use ENTITY_TYPE
     */
    @Overwrite
    public static Entity get(String entityTypeId, CRBinDeserializer deserial) {
        Identifier entityId = Identifier.of(entityTypeId);
        if (!PuzzleRegistries.ENTITY_TYPE.contains(entityId)) return null;

        return PuzzleRegistries.ENTITY_TYPE.get(entityId).creator().apply(deserial);
    }

    /**
     * @author Mr_Zombii
     * @reason Force game to use ENTITY_TYPE
     */
    @Overwrite
    public static void registerEntityCreator(String entityTypeId, Function<CRBinDeserializer, Entity> creator) {
        PuzzleRegistries.ENTITY_TYPE.store(new EntityType(
                Identifier.of(entityTypeId),
                creator.apply(null).getClass(),
                creator
        ));
    }

}
