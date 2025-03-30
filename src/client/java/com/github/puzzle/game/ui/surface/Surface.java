package com.github.puzzle.game.ui.surface;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.gamestates.GameState;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.puzzle.game.ui.surface.SurfaceGameState.MAIN_INSTANCE;

public interface Surface {

    AtomicReference<Surface> CURRENT_SURFACE = new AtomicReference<>();
    AtomicReference<Surface> LAST_SURFACE = new AtomicReference<>();

    AtomicReference<GameState> CURRENT_GAMESTATE = new AtomicReference<>(GameState.currentGameState);
    AtomicReference<GameState> LAST_GAMESTATE = new AtomicReference<>(GameState.currentGameState);

    static Surface switchToSurface(Surface surface) {
        if (surface.equals(CURRENT_SURFACE.get())) return surface;

        Threads.runOnMainThread(() -> {
            if (GameState.currentGameState != MAIN_INSTANCE) {
                GameState.switchToGameState(MAIN_INSTANCE);
            }

            LAST_SURFACE.set(CURRENT_SURFACE.get());
            CURRENT_SURFACE.set(surface);

            MAIN_INSTANCE.changeScreen(surface);
        });

        return surface;
    }

    void init();
    boolean isInitialized();

    void render();
    void update(float delta);

    Viewport getViewport();
    Camera getCamera();
    Batch getBatch();

    void setViewport(Viewport viewport);
    void setBatch(Batch batch);
}
