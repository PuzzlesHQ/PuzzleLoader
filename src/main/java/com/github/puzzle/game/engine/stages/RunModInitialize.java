package com.github.puzzle.game.engine.stages;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.engine.stage.AbstractStage;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer;
import com.github.puzzle.core.loader.util.ModLocator;
import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.events.OnRegisterLanguageEvent;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class RunModInitialize extends AbstractStage {

    public static Queue<Initializer> initializers = new LinkedList<>();

    static {
        initializers.add(new Initializer<>(
                Constants.SIDE,
                ModInitializer.ENTRYPOINT_KEY,
                ModInitializer.class,
                ModInitializer::onInit
        ));
    }

    public RunModInitialize() {
        super("Initializing Mods - Init");
    }

    @Override
    public void doStage() {
        tasks.add(() -> {
            loader.bar2.setVisible(true);
            while (!getInitializers().isEmpty()) {
                Initializer initializer = getInitializers().remove();

                ModLocator.getMods(initializer.envType);

                try {
                    ModLocator.locatedMods.get(Constants.MOD_ID).invokeEntrypoint(initializer.key, initializer.clazz, initializer.invoker);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                AtomicInteger counter = new AtomicInteger(0);
                loader.bar2.setProgress(0);
                loader.bar2.setMax(ModLocator.locatedMods.size() - 2);
                ModLocator.locatedMods.values().forEach(container -> {
                    if (!Objects.equals(container.ID, Constants.MOD_ID)) {
                        try {
                            loader.bar2.setProgress(counter.incrementAndGet());
                            container.invokeEntrypoint(initializer.key, initializer.clazz, initializer.invoker);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            PuzzleRegistries.EVENT_BUS.post(new OnRegisterLanguageEvent());
            loader.bar2.setVisible(false);
        });
    }

    protected Queue<Initializer> getInitializers() {
        return initializers;
    }

    public record Initializer<T>(
            EnvType envType,
            String key,
            Class<T> clazz,
            Consumer<? super T> invoker
    ) {}

}
