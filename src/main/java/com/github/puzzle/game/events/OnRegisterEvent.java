package com.github.puzzle.game.events;

import com.github.puzzle.core.registries.IRegistry;
import finalforeach.cosmicreach.util.Identifier;

public class OnRegisterEvent {

    public final IRegistry registry;
    public final Identifier id;
    public final Object obj;

    public OnRegisterEvent(IRegistry registry, Identifier id, Object obj) {
        this.registry = registry;
        this.obj = obj;
        this.id = id;
    }

}
