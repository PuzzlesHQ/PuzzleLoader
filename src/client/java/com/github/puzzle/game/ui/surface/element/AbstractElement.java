package com.github.puzzle.game.ui.surface.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.puzzle.game.ui.surface.Surface;

public abstract class AbstractElement implements Element {

    public int x, y;
    protected AnchorX anchorX = AnchorX.NONE;
    protected AnchorY anchorY = AnchorY.NONE;
    boolean visible = true;

    protected Color tmp = new Color();
    protected Vector2 textDim = new Vector2();

    @Override
    public void update(Surface surface, float delta) {}

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setAnchorX(AnchorX anchorX) {
        this.anchorX = anchorX;
    }

    @Override
    public void setAnchorY(AnchorY anchorY) {
        this.anchorY = anchorY;
    }

    @Override
    public AnchorX getAnchorX() {
        return anchorX;
    }

    @Override
    public AnchorY getAnchorY() {
        return anchorY;
    }
}
