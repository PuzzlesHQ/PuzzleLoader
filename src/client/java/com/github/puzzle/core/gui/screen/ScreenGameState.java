package com.github.puzzle.core.gui.screen;

import com.badlogic.gdx.Gdx;
import com.github.puzzle.core.input.GLFWCoreInputProcessor;
import finalforeach.cosmicreach.gamestates.GameState;

public class ScreenGameState extends GameState {

    public ScreenImpl screen;

    public ScreenGameState() {}

    public ScreenGameState(ScreenImpl screen) {
        this.screen = screen;
    }

    public void changeScreen(ScreenImpl screen) {
        this.screen = screen;

        GLFWCoreInputProcessor.setProcessor(screen);
    }

    @Override
    public void create() {
        super.create();
        changeScreen(new ScreenImpl(uiViewport, GameState.batch));
    }

    private int curWidth;
    private int curHeight;

    public void resize(int width, int height) {
        this.curWidth = width;
        this.curHeight = height;
        if (screen != null && screen.getViewport() != null) {
            screen.getViewport().update(width, height);
            screen.getBatch().setProjectionMatrix(screen.getViewport().getCamera().combined);
        }

    }

    @Override
    public void render() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        if (this.curWidth != screenWidth || this.curHeight != screenHeight) {
            this.resize(screenWidth, screenHeight);
        }

        if (screen != null)
            screen.render();
    }

    @Override
    public void onSwitchTo() {
        GLFWCoreInputProcessor.setProcessor(screen);
    }
}