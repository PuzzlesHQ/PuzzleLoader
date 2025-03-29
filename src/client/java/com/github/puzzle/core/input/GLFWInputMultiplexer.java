package com.github.puzzle.core.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GLFWInputMultiplexer implements GLFWInputProcessor {

    List<GLFWInputProcessor> processors;

    public GLFWInputMultiplexer() {
        processors = new ArrayList<>();
    }

    public GLFWInputMultiplexer(GLFWInputProcessor... processors) {
        this();

        this.processors.addAll(Arrays.asList(processors));
    }

    public void addProcessors(GLFWInputProcessor... processors) {
        this.processors.addAll(Arrays.asList(processors));
    }

    @Override
    public void onScroll(long window, double xOffset, double yOffset) {
        for (GLFWInputProcessor processor : this.processors)
            processor.onScroll(window, xOffset, yOffset);
    }

    @Override
    public void onCursorEnter(long window, boolean entered) {
        for (GLFWInputProcessor processor : this.processors)
            processor.onCursorEnter(window, entered);
    }

    @Override
    public void onKeyPress(long window, int key, int scancode, int action, int mods) {
        for (GLFWInputProcessor processor : this.processors)
            processor.onKeyPress(window, key, scancode, action, mods);
    }

    @Override
    public void onCharTyped(long window, int codepoint) {
        for (GLFWInputProcessor processor : this.processors)
            processor.onCharTyped(window, codepoint);
    }

    @Override
    public void onFileDropped(long window, int count, long paths) {
        for (GLFWInputProcessor processor : this.processors)
            processor.onFileDropped(window, count, paths);
    }

    @Override
    public void onMouseClick(long window, int button, int action, int mods) {
        for (GLFWInputProcessor processor : this.processors)
            processor.onMouseClick(window, button, action, mods);
    }

    @Override
    public void onCursorMove(long window, double x, double y) {
        for (GLFWInputProcessor processor : this.processors)
            processor.onCursorMove(window, x, y);
    }
}
