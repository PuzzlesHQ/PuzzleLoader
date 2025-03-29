package com.github.puzzle.game.mixins.client.input;

import com.badlogic.gdx.backends.lwjgl3.DefaultLwjgl3Input;
import com.github.puzzle.core.input.GDXInputAdapter;
import com.github.puzzle.core.input.GLFWCoreInputProcessor;
import org.lwjgl.glfw.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DefaultLwjgl3Input.class)
public class GdxInputProcessorMixin {

    @Shadow private GLFWKeyCallback keyCallback;

    @Shadow private GLFWCharCallback charCallback;

    @Shadow private GLFWScrollCallback scrollCallback;

    @Shadow private GLFWCursorPosCallback cursorPosCallback;

    @Shadow private GLFWMouseButtonCallback mouseButtonCallback;

    /**
     * @author Mr_Zombii
     * @reason Keep gdx input processing functional && add custom input processing.
     */
    @Overwrite
    public void windowHandleChanged(long windowHandle) {
        GDXInputAdapter adapter = GLFWCoreInputProcessor.getGdxAdapter();
        adapter.setKeyCallback(this.keyCallback);
        adapter.setCharCallback(this.charCallback);
        adapter.setScrollCallback(this.scrollCallback);
        adapter.setCursorPosCallback(this.cursorPosCallback);
        adapter.setMouseButtonCallback(this.mouseButtonCallback);

        GLFWCoreInputProcessor.registerCallbacks(windowHandle);
    }

}
