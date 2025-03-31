package com.github.puzzle.game.engine.stages;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.PostModInitializer;

import java.util.LinkedList;
import java.util.Queue;

public class RunModPostInitialize extends RunModInitialize {

    public static Queue<Initializer> initializers = new LinkedList<>();

    static {
        initializers.add(new Initializer<>(
                Constants.SIDE,
                PostModInitializer.ENTRYPOINT_KEY,
                PostModInitializer.class,
                PostModInitializer::onPostInit
        ));
    }

    public RunModPostInitialize() {
        name = "Initializing Mods - Post";
    }

    @Override
    protected Queue<Initializer> getInitializers() {
        return initializers;
    }
}
