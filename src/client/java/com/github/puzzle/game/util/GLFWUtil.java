package com.github.puzzle.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.math.Vector2;
import com.github.puzzle.core.loader.util.Reflection;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;

public class GLFWUtil {

    public static Lwjgl3Window getCurrentWindow() {
        return Reflection.getFieldContents(Gdx.app, "currentWindow");
    }

    public static long getWindowPtr(Lwjgl3Window window) {
        return window.getWindowHandle();
    }

    public static Vector2 getMousePos(Lwjgl3Window window) {
        long ptr = window.getWindowHandle();

        return from((a, b) -> GLFW.glfwGetCursorPos(ptr, a, b));
    }

    public static boolean isLeftClickDown(Lwjgl3Window window) {
        long ptr = window.getWindowHandle();

        return GLFW.glfwGetMouseButton(ptr, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_TRUE;
    }

    private static final double[] xStatic = new double[1];
    private static final double[] yStatic = new double[1];

    public static Vector2 from(BiConsumer<double[], double[]> consumer) {
        consumer.accept(xStatic, yStatic);
        return new Vector2((float) xStatic[0], (float) yStatic[0]);
    }

}
