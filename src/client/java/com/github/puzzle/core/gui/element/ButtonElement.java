package com.github.puzzle.core.gui.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.github.puzzle.core.gui.Surface;
import com.github.puzzle.game.ClientGlobals;
import com.github.puzzle.core.input.GLFWInputProcessor;
import com.github.puzzle.game.engine.rendering.text.FormatText;
import com.github.puzzle.game.engine.rendering.text.FormattedTextRenderer;
import com.github.puzzle.game.util.GLFWUtil;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import org.lwjgl.glfw.GLFW;

public class ButtonElement extends AbstractElement implements GLFWInputProcessor {

    int width = 250;
    int height = 50;
    int thickness;

    Color bg, outline, hvr;
    private String text;

    public ButtonElement(Color bg, Color outline, Color hvr) {
        this(bg, outline, hvr, 2);
    }

    public ButtonElement(Color bg, Color outline, Color hvr, int thickness) {
        this.bg = bg;
        this.outline = outline;
        this.thickness = thickness;
        this.hvr = hvr;
    }

    public void setText(String text) {
        this.text = text;
    }

    Surface surface;

    @Override
    public void render(Surface surface) {
        if (this.surface == null) this.surface = surface;

        Batch batch = surface.getBatch();

        tmp.set(batch.getColor());

        batch.setColor(isHovering() ? hvr : outline);
        batch.draw(ClientGlobals.whitePixel, x, y, width + thickness, height + thickness);

        batch.setColor(bg);
        batch.draw(ClientGlobals.whitePixel, x + (thickness / 2f), y + (thickness / 2f), width, height);

        if (!text.isEmpty()) {
            FormatText formatted = FormatText.of(text);
            batch.setColor(Color.WHITE);
            FontRenderer.getTextDimensions(surface.getViewport(), formatted.getText(), textDim);
            FormattedTextRenderer.drawText(batch, surface.getViewport(), formatted, x + (width / 2f), y + (height / 2f), HorizontalAnchor.CENTERED, VerticalAnchor.CENTERED);
        }

        batch.setColor(tmp);
    }

    int clickCount = 0;

    public boolean isHovering() {
        Vector2 mousePos = GLFWUtil.getMousePos(GLFWUtil.getCurrentWindow());
        Vector2 projMouse = surface.getViewport().unproject(mousePos);

        return projMouse.x >= x && projMouse.x <= x + width && projMouse.y >= y && projMouse.y <= y + height;
    }

    int oldState = -1;
    int newState;

    @Override
    public void onMouseClick(long window, int button, int action, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            oldState = newState;
            newState = action;
        }

        if (oldState != -1 && oldState != newState && newState == GLFW.GLFW_RELEASE && isHovering()) {
            System.out.println("CLICK!!! #" + ++clickCount);
        }
    }
}
