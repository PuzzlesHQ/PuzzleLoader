package com.github.puzzle.game.entity;

import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.util.Identifier;

import java.util.function.Function;

public record EntityType(
        Identifier id,
        Class<? extends Entity> klass,
        Function<CRBinDeserializer, Entity> creator
) {
}
