package com.github.puzzle.core.input;

import org.lwjgl.glfw.*;

public class GDXInputAdapter implements GLFWInputProcessor {

    GLFWScrollCallbackI scrollCallback;
    GLFWCursorEnterCallbackI cursorEnterCallback;
    GLFWCursorPosCallbackI cursorPosCallback;
    GLFWMouseButtonCallbackI mouseButtonCallback;

    GLFWKeyCallbackI keyCallback;
    GLFWCharCallbackI charCallback;

    GLFWDropCallbackI dropCallback;

    @Override
    public void onScroll(long window, double xOffset, double yOffset) {
        if (scrollCallback != null)
            scrollCallback.invoke(window, xOffset, yOffset);
    }

    @Override
    public void onCursorEnter(long window, boolean entered) {
        if (cursorEnterCallback != null)
            cursorEnterCallback.invoke(window, entered);
    }

    @Override
    public void onKeyPress(long window, int key, int scancode, int action, int mods) {
        if (keyCallback != null)
            keyCallback.invoke(window, key, scancode, action, mods);
    }

    @Override
    public void onCharTyped(long window, int codepoint) {
        if (charCallback != null)
            charCallback.invoke(window, codepoint);
    }

    @Override
    public void onFileDropped(long window, int count, long paths) {
        if (dropCallback != null)
            dropCallback.invoke(window, count, paths);
    }

    @Override
    public void onMouseClick(long window, int button, int action, int mods) {
        if (mouseButtonCallback != null)
            mouseButtonCallback.invoke(window, button, action, mods);
    }

    @Override
    public void onCursorMove(long window, double x, double y) {
        if (cursorPosCallback != null)
            cursorPosCallback.invoke(window, x, y);
    }

    public void setKeyCallback(GLFWKeyCallbackI keyCallback) {
        this.keyCallback = keyCallback;
    }

    public void setCharCallback(GLFWCharCallbackI charCallback) {
        this.charCallback = charCallback;
    }

    public void setScrollCallback(GLFWScrollCallbackI scrollCallback) {
        this.scrollCallback = scrollCallback;
    }

    public void setCursorPosCallback(GLFWCursorPosCallbackI cursorPosCallback) {
        this.cursorPosCallback = cursorPosCallback;
    }

    public void setMouseButtonCallback(GLFWMouseButtonCallbackI mouseButtonCallback) {
        this.mouseButtonCallback = mouseButtonCallback;
    }
}
