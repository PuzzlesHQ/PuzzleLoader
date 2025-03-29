package com.github.puzzle.game.ui.surface;

import com.github.puzzle.core.Constants;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.gamestates.GameState;

import java.util.concurrent.TimeUnit;

public class SurfaceUpdateRunner {

    public static Surface currentSurface = null;

    private static float delta;

    public static Thread updateRunner;

    public static void start() {
        updateRunner = new Thread(SurfaceUpdateRunner::startUpdateRunner, "Game-Loader");
//        updateRunner.setUncaughtExceptionHandler();
        updateRunner.setDaemon(true);
        updateRunner.start();
    }

    private static void startUpdateRunner() {
        while (!Constants.shouldClose()) {
            long start = System.nanoTime();

            if (currentSurface == null)
                Threads.runOnMainThread(() -> {
                    if (currentSurface == null && GameState.currentGameState instanceof SurfaceGameState)
                        currentSurface = ((SurfaceGameState) GameState.currentGameState).getSurface();
                });
            if (currentSurface != null)
                currentSurface.update(delta);

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            long end = System.nanoTime();
            delta = TimeUnit.NANOSECONDS.toMillis(end - start) / 1000f;
        }
    }

}