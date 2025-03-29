package com.github.puzzle.core.gui.screen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.puzzle.core.gui.Surface;
import com.github.puzzle.core.gui.element.Element;
import com.github.puzzle.core.input.GLFWInputProcessor;

import java.util.ArrayList;
import java.util.List;

public class ScreenImpl implements GLFWInputProcessor, Surface {

    private Viewport vp;
    private Camera cam;
    private Batch batch;
    private List<Element> elements;

    public Color bgColor;

    public ScreenImpl(Viewport vp) {
        this(vp, new SpriteBatch());
    }

    public ScreenImpl(Viewport vp, Batch batch) {
        if (vp != null && batch != null) {
            this.vp = vp;
            this.cam = vp.getCamera();
            this.batch = batch;

            batch.setColor(Color.WHITE);
        }

        this.elements = new ArrayList<>();

        bgColor = new Color(0, 0, 0, 1);
    }

    boolean isInitialized;

    public void init() {
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void render() {
        ScreenUtils.clear(bgColor, true);

        batch.begin();
        for (Element element : elements) {
            if (element.isVisible())
                element.render(this);
        }
        batch.end();
    }

    public void update(float delta) {
        for (Element element : elements) {
            if (element.isVisible())
                element.update(this, delta);
        }
    }

    @Override
    public Viewport getViewport() {
        return vp;
    }

    @Override
    public Camera getCamera() {
        return cam;
    }

    @Override
    public Batch getBatch() {
        return batch;
    }

    @Override
    public void setViewport(Viewport viewport) {
        this.vp = viewport;
        this.cam = viewport.getCamera();
    }

    @Override
    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public void add(Element element) {
        this.elements.add(element);
    }

    public void remove(Element element) {
        this.elements.remove(element);
    }

    public void remove(int i) {
        this.elements.remove(i);
    }

    public void clear() {
        this.elements.clear();
    }

    @Override
    public void onCharTyped(long window, int codepoint) {
        for (Element e : elements) {
            if (e instanceof GLFWInputProcessor element)
                element.onCharTyped(window, codepoint);
        }
    }

    @Override
    public void onCursorEnter(long window, boolean entered) {
        for (Element e : elements) {
            if (e instanceof GLFWInputProcessor element)
                element.onCursorEnter(window, entered);
        }
    }

    @Override
    public void onCursorMove(long window, double x, double y) {
        for (Element e : elements) {
            if (e instanceof GLFWInputProcessor element)
                element.onCursorMove(window, x, y);
        }
    }

    @Override
    public void onFileDropped(long window, int count, long paths) {
        for (Element e : elements) {
            if (e instanceof GLFWInputProcessor element)
                element.onFileDropped(window, count, paths);
        }
    }

    @Override
    public void onKeyPress(long window, int key, int scancode, int action, int mods) {
        for (Element e : elements) {
            if (e instanceof GLFWInputProcessor element)
                element.onKeyPress(window, key, scancode, action, mods);
        }
    }

    @Override
    public void onMouseClick(long window, int button, int action, int mods) {
        for (Element e : elements) {
            if (e instanceof GLFWInputProcessor element)
                element.onMouseClick(window, button, action, mods);
        }
    }

    @Override
    public void onScroll(long window, double xOffset, double yOffset) {
        for (Element e : elements) {
            if (e instanceof GLFWInputProcessor element)
                element.onScroll(window, xOffset, yOffset);
        }
    }

}
