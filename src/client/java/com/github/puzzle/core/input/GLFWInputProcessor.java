package com.github.puzzle.core.input;

public interface GLFWInputProcessor {

    default void onScroll(long window, double xOffset, double yOffset){}
    default void onCursorEnter(long window, boolean entered){}
    default void onMouseClick(long window, int button, int action, int mods){}
    default void onCursorMove(long window, double x, double y){}

    default void onKeyPress(long window, int key, int scancode, int action, int mods){}
    default void onCharTyped(long window, int codepoint){}

    default void onFileDropped(long window, int count, long paths){}

}
