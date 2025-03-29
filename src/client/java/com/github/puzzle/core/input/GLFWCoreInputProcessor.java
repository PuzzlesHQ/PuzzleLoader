package com.github.puzzle.core.input;

import finalforeach.cosmicreach.Threads;
import org.lwjgl.glfw.GLFW;

public class GLFWCoreInputProcessor {

    private static GLFWInputProcessor CURRENT_PROCESSOR;
    private static final GDXInputAdapter GDX_INPUT_ADAPTER = new GDXInputAdapter();

    public static GLFWInputProcessor getProcessor() {
        return CURRENT_PROCESSOR;
    }

    public static void setProcessor(GLFWInputProcessor inputProcessor) {
        CURRENT_PROCESSOR = inputProcessor;
    }

    public static void registerCallbacks(long window) {
        Threads.runOnMainThread(() -> {
            try {
                GLFW.glfwSetMouseButtonCallback(window, GLFWCoreInputProcessor::onMouseClick);
                GLFW.glfwSetCursorPosCallback(window, GLFWCoreInputProcessor::onCursorMove);
                GLFW.glfwSetScrollCallback(window, GLFWCoreInputProcessor::onScroll);
                GLFW.glfwSetCursorEnterCallback(window, GLFWCoreInputProcessor::onCursorEnter);

                GLFW.glfwSetCharCallback(window, GLFWCoreInputProcessor::onCharTyped);
                GLFW.glfwSetKeyCallback(window, GLFWCoreInputProcessor::onKeyPress);

                GLFW.glfwSetDropCallback(window, GLFWCoreInputProcessor::onFileDropped);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void onScroll(long window, double xOffset, double yOffset) {
        if (CURRENT_PROCESSOR != null)
            CURRENT_PROCESSOR.onScroll(window, xOffset, yOffset);
        GDX_INPUT_ADAPTER.onScroll(window, xOffset, yOffset);
    }

    private static void onCursorEnter(long window, boolean entered) {
        if (CURRENT_PROCESSOR != null)
            CURRENT_PROCESSOR.onCursorEnter(window, entered);
        GDX_INPUT_ADAPTER.onCursorEnter(window, entered);
    }

    private static void onKeyPress(long window, int key, int scancode, int action, int mods) {
        if (CURRENT_PROCESSOR != null)
            CURRENT_PROCESSOR.onKeyPress(window, key, scancode, action, mods);
        GDX_INPUT_ADAPTER.onKeyPress(window, key, scancode, action, mods);
    }

    private static void onCharTyped(long window, int codepoint) {
        if (CURRENT_PROCESSOR != null)
            CURRENT_PROCESSOR.onCharTyped(window, codepoint);
        GDX_INPUT_ADAPTER.onCharTyped(window, codepoint);
    }

    private static void onFileDropped(long window, int count, long names) {
        if (CURRENT_PROCESSOR != null)
            CURRENT_PROCESSOR.onFileDropped(window, count, names);
        GDX_INPUT_ADAPTER.onFileDropped(window, count, names);
    }

    public static void onMouseClick(long window, int button, int action, int mods) {
        if (CURRENT_PROCESSOR != null)
            CURRENT_PROCESSOR.onMouseClick(window, button, action, mods);
        GDX_INPUT_ADAPTER.onMouseClick(window, button, action, mods);
    }

    public static void onCursorMove(long window, double x, double y) {
        if (CURRENT_PROCESSOR != null)
            CURRENT_PROCESSOR.onCursorMove(window, x, y);
        GDX_INPUT_ADAPTER.onCursorMove(window, x, y);
    }


    public static GDXInputAdapter getGdxAdapter() {
        return GDX_INPUT_ADAPTER;
    }
}
