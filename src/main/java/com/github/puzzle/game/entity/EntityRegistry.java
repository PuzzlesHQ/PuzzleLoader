package com.github.puzzle.game.entity;

import com.github.puzzle.core.registries.MapRegistry;
import com.github.puzzle.core.registries.RegistryObject;
import finalforeach.cosmicreach.util.Identifier;

import java.util.LinkedHashMap;

public class EntityRegistry extends MapRegistry<EntityType> {
    public EntityRegistry(Identifier identifier) {
        super(identifier, new LinkedHashMap<>(), true, true);
    }

    public RegistryObject<EntityType> store(EntityType registry) {
        return super.store(registry.id(), registry);
    }
}