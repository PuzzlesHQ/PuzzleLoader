package com.github.puzzle.game.ui.surface;

import com.badlogic.gdx.Gdx;
import com.github.puzzle.core.input.GLFWCoreInputProcessor;
import com.github.puzzle.core.input.GLFWInputProcessor;
import finalforeach.cosmicreach.gamestates.GameState;

public class SurfaceGameState extends GameState {

    public static final SurfaceGameState MAIN_INSTANCE = new SurfaceGameState();

    public Surface surface;

    public SurfaceGameState() {}

    public SurfaceGameState(Surface screen) {
        this.surface = screen;
    }

    public void changeScreen(Surface screen) {
        if (this.surface == screen) return;

        this.surface = screen;

        screen.setBatch(GameState.batch);
        screen.setViewport(uiViewport);

        if (!screen.isInitialized())
            screen.init();

        if (screen instanceof GLFWInputProcessor)
            GLFWCoreInputProcessor.setProcessor((GLFWInputProcessor) screen);
    }

    @Override
    public void create() {
        super.create();
    }

    private int curWidth;
    private int curHeight;

    public void resize(int width, int height) {
        this.curWidth = width;
        this.curHeight = height;
        if (surface != null && surface.getViewport() != null) {
            surface.getViewport().update(width, height);
            surface.getBatch().setProjectionMatrix(surface.getViewport().getCamera().combined);
        }

    }

    @Override
    public void render() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        if (this.curWidth != screenWidth || this.curHeight != screenHeight) {
            this.resize(screenWidth, screenHeight);
        }

        if (surface != null)
            surface.render();
    }

    @Override
    public void onSwitchTo() {
        if (surface instanceof GLFWInputProcessor)
            GLFWCoreInputProcessor.setProcessor((GLFWInputProcessor) surface);
    }

    @Override
    public void switchAwayTo(GameState gameState) {
        SurfaceUpdateRunner.currentSurface = null;
    }

    public Surface getSurface() {
        return surface;
    }
}