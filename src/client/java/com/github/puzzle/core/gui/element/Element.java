package com.github.puzzle.core.gui.element;

import com.github.puzzle.core.gui.Surface;

public interface Element {

    void render(Surface screen);
    void update(Surface screen, float delta);

    void setVisible(boolean b);
    boolean isVisible();

    void setPos(int x, int y);
    void setX(int x);
    void setY(int y);

    int getX();
    int getY();

    void setAnchorX(AnchorX alignment);
    void setAnchorY(AnchorY alignment);
    AnchorX getAnchorX();
    AnchorY getAnchorY();

    enum AnchorX {
        NONE,
        RIGHT,
        LEFT,
        CENTER
    }

    enum AnchorY {
        NONE,
        TOP,
        BOTTOM,
        CENTER
    }

}