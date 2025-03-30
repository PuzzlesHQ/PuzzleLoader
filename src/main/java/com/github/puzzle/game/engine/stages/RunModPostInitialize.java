package com.github.puzzle.game.engine.stages;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.engine.stage.AbstractStage;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.PostModInitializer;
import com.github.puzzle.core.loader.util.ModLocator;
import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.events.OnRegisterLanguageEvent;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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
