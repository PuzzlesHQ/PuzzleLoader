package com.github.puzzle.game.ui.surface.element;

import com.badlogic.gdx.math.MathUtils;
import com.github.puzzle.game.ui.surface.Surface;
import com.github.puzzle.game.ui.surface.SurfaceImpl;
import org.lwjgl.glfw.GLFW;

public class TextInputElement extends ButtonElement {
    public SurfaceImpl surface;
    public String text = "";
    public int cursorIndex = 0;
    public float blinkTimer = 0f;
    public float blinkInterval = 1.5f;
    public float halfBlinkInterval = blinkInterval / 2f;

    public TextInputElement(SurfaceImpl surface) {
        super();
        this.surface = surface;
    }

    @Override
    public void update(Surface surface, float delta) {
        super.update(surface, delta);

        if (this.surface.activeInPutElement == this) {
            blinkTimer += delta;

            if (blinkTimer >= blinkInterval) {
                blinkTimer -= blinkInterval;
            }
            updateText();
        }
    }

    public void updateText(){
        String text = this.text;
        if (this.surface.activeInPutElement == this && blinkTimer > halfBlinkInterval) {
            cursorIndex = MathUtils.clamp(cursorIndex, 0, text.length());
            text = new StringBuilder(text).insert(cursorIndex, "§[a3a3a3]|§[ffffff]").toString();
        }
        super.setText(text);
    }

    @Override
    public void onMouseClick(long window, int button, int action, int mods) {
        super.onMouseClick(window, button, action, mods);
        if (surface.activeInPutElement == this && !isHovering) {
            onDeselect();
        }
    }

    @Override
    public void onPress() {
        super.onPress();
        surface.activeInPutElement = this;
    }

    @Override
    public void onKeyPress(long window, int key, int scancode, int action, int mods) {
        super.onKeyPress(window, key, scancode, action, mods);
        if (surface.activeInPutElement == this){
            boolean ctrlPressed = mods == GLFW.GLFW_MOD_CONTROL;
            boolean isUpperCase = mods == GLFW.GLFW_MOD_SHIFT;
            switch (key) {
                case GLFW.GLFW_KEY_C:
                    if (action == GLFW.GLFW_PRESS && ctrlPressed) {
                        onCtrlC(window);
                    }
                    break;
                case GLFW.GLFW_KEY_V:
                    if (action == GLFW.GLFW_PRESS && ctrlPressed) {
                        onCtrlV(window);
                    }
                    break;
                case GLFW.GLFW_KEY_DELETE:
                    switch (action) {
                        case GLFW.GLFW_PRESS -> onDeletePressed();
                        case GLFW.GLFW_REPEAT -> onDeleteRepeat();
                    }
                    break;
                case GLFW.GLFW_KEY_BACKSPACE:
                    switch (action) {
                        case GLFW.GLFW_PRESS -> onBackSpacePressed();
                        case GLFW.GLFW_REPEAT -> onBackSpaceRepeat();
                    }
                    break;
                case GLFW.GLFW_KEY_LEFT:
                    switch (action) {
                        case GLFW.GLFW_PRESS -> moveCursorLeftPressed();
                        case GLFW.GLFW_REPEAT -> moveCursorLeftRepeat();
                    }
                    break;
                case GLFW.GLFW_KEY_RIGHT:
                    switch (action) {
                        case GLFW.GLFW_PRESS -> moveCursorRightPressed();
                        case GLFW.GLFW_REPEAT -> moveCursorRightRepeat();
                    }
                    break;
                default:
                    String keyText = GLFW.glfwGetKeyName(key, scancode);
                    switch (action) {
                        case GLFW.GLFW_PRESS -> onKeyPressed(key, isUpperCase, keyText);
                        case GLFW.GLFW_REPEAT -> onKeyRepeat(key, isUpperCase, keyText);
                    }
            }
        }
    }

    public void onDeselect(){
        surface.activeInPutElement = null;
        updateText();
    }

    public void onKeyPressed(int key, boolean isUpperCase, String keyText) {
        if(keyText != null && !keyText.isEmpty()){
            if (isUpperCase) keyText = keyText.toUpperCase();
            text = new StringBuilder(text).insert(cursorIndex, keyText).toString();
            cursorIndex +=1;
            updateText();
        } else if (key == GLFW.GLFW_KEY_SPACE) {
            text = new StringBuilder(text).insert(cursorIndex, " ").toString();
            cursorIndex +=1;
            updateText();
        }
    }

    public void onKeyRepeat(int key, boolean isUpperCase, String keyText) {
        if(keyText != null && !keyText.isEmpty()){
            if (isUpperCase) keyText = keyText.toUpperCase();
            text = new StringBuilder(text).insert(cursorIndex, keyText).toString();
            cursorIndex +=1;
            updateText();

        } else if (key == GLFW.GLFW_KEY_SPACE) {
            text = new StringBuilder(text).insert(cursorIndex, " ").toString();
            cursorIndex +=1;
            updateText();
        }
    }

    public void onDeletePressed() {
        if (cursorIndex < text.length()) {
            text = new StringBuilder(text).deleteCharAt(cursorIndex).toString();
            updateText();
        }
    }

    public void onDeleteRepeat() {
        if (cursorIndex < text.length()) {
            text = new StringBuilder(text).deleteCharAt(cursorIndex).toString();
            updateText();
        }
    }

    public void onBackSpacePressed() {
        if (cursorIndex < text.length() + 1) {
            if (cursorIndex - 1 >= 0){
                text = new StringBuilder(text).deleteCharAt(cursorIndex - 1).toString();
                cursorIndex -= 1;
                updateText();
            }
        }
    }

    public void onBackSpaceRepeat() {
        if (cursorIndex < text.length() + 1 && cursorIndex < text.length()) {
            if (cursorIndex - 1 >= 0){
                text = new StringBuilder(text).deleteCharAt(cursorIndex - 1).toString();
                cursorIndex -= 1;
                updateText();
            }
        }
    }

    public void onCtrlV(long window) {
        String clipboardText = GLFW.glfwGetClipboardString(window);
        if (clipboardText != null) {
            this.text = clipboardText;
            updateText();
        }
    }

    public void onCtrlC(long window) {
        GLFW.glfwSetClipboardString(window, this.text);
    }

    public void moveCursorLeftPressed() {
        if (cursorIndex > 0) {
            cursorIndex--;
        }
    }

    public void moveCursorRightPressed() {
        if (cursorIndex < text.length()) {
            cursorIndex++;
        }
    }

    public void moveCursorLeftRepeat() {
        if (cursorIndex > 0) {
            cursorIndex--;
        }
    }

    public void moveCursorRightRepeat() {
        if (cursorIndex < text.length()) {
            cursorIndex++;
        }
    }

    @Override
    public void setText(String text) {
        this.text = text;
        updateText();
    }

    @Override
    public String getText() {
        return this.text;
    }
}
