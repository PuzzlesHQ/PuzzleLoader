package com.github.puzzle.game.ui.surface.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.puzzle.game.ui.surface.Surface;
import com.github.puzzle.game.ClientGlobals;
import com.github.puzzle.core.input.GLFWInputProcessor;
import com.github.puzzle.game.engine.rendering.text.FormatText;
import com.github.puzzle.game.engine.rendering.text.FormattedTextRenderer;
import com.github.puzzle.game.util.GLFWUtil;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;

public class ButtonElement extends AbstractElement implements GLFWInputProcessor {

    public static final Style DEFAULT_STYLE = new Style(
            Color.BLACK,
            Color.GRAY,
            Color.BLACK,
            Color.WHITE,
            Color.DARK_GRAY,
            Color.WHITE,
            2
    );

    int width = 250;
    int height = 50;
    int thickness;

    Color bg, outline;
    private String text = "";

    private Style style;

    public ButtonElement() {
        this(DEFAULT_STYLE);
    }

    public ButtonElement(String text) {
        this(DEFAULT_STYLE, text);
    }

    public ButtonElement(Style style) {
        this(style, "");
    }

    public ButtonElement(Style style, String text) {
        this.style = style;
        this.text = text;

        this.outline = style.defaultOutline;
        this.bg = style.defaultBackground;
        this.thickness = style.outlineThickness;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    Surface surface;

    boolean isHovering = false;

    @Override
    public void render(Surface surface) {
        if (this.surface == null) this.surface = surface;

        float vx = getVisualX(surface.getViewport(), this);
        float vy = getVisualY(surface.getViewport(), this);

        Batch batch = surface.getBatch();

        tmp.set(batch.getColor());

        if (isHovering() && !isHovering) {
            isHovering = true;
            onStartHover();
        } else if (!isHovering() && isHovering) {
            isHovering = false;
            onStopHover();
        }

        batch.setColor(outline);
        batch.draw(ClientGlobals.whitePixel, vx, vy, width + thickness, height + thickness);

        batch.setColor(bg);
        batch.draw(ClientGlobals.whitePixel, vx + (thickness / 2f), vy + (thickness / 2f), width, height);

        if (!getText().isEmpty()) {
            FormatText formatted = FormatText.of(getText());
            batch.setColor(Color.WHITE);
            FontRenderer.getTextDimensions(surface.getViewport(), formatted.getText(), textDim);
            FormattedTextRenderer.drawText(batch, surface.getViewport(), formatted, vx + (width / 2f), vy + (height / 2f), HorizontalAnchor.CENTERED, VerticalAnchor.CENTERED);
        }

        batch.setColor(tmp);
    }

    public boolean isHovering() {
        float vx = getVisualX(surface.getViewport(), this);
        float vy = getVisualY(surface.getViewport(), this);

        Vector2 mousePos = GLFWUtil.getMousePos(GLFWUtil.getCurrentWindow());
        Vector2 projMouse = surface.getViewport().unproject(mousePos);

        return projMouse.x >= vx && projMouse.x <= vx + width && projMouse.y >= vy && projMouse.y <= vy + height;
    }

    int oldState = -1;
    int newState;

    @Override
    public void onMouseClick(long window, int button, int action, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            oldState = newState;
            newState = action;
        }

        if (oldState != -1 && (newState == GLFW.GLFW_PRESS) && isHovering()) {
            onPress();
        }
        if (oldState != -1 && oldState != newState && newState == GLFW.GLFW_RELEASE && isHovering()) {
            onRelease();
        }
    }

    public void onPress() {
        bg = style.getPressBackground();
        outline = style.getPressOutline();
    }

    public void onRelease() {
        bg = isHovering() ? style.getHoverBackground() : style.defaultBackground;
        outline = isHovering() ? style.getHoverOutline() : style.defaultOutline;
    }

    public void onStartHover() {
        bg = style.getHoverBackground();
        outline = style.getHoverOutline();
    }

    public void onStopHover() {
        bg = style.defaultBackground;
        outline = style.defaultOutline;
    }

    protected static float getVisualX(Viewport viewport, ButtonElement element) {
        return switch (element.anchorX) {
            case NONE -> element.x;
            case RIGHT -> element.x + (viewport.getWorldWidth() * .5f) - (element.width + element.thickness);
            case LEFT -> element.x - (viewport.getWorldWidth() * .5f);
            case CENTER -> element.x - (element.width * .5f);
        };
    }

    protected static float getVisualY(Viewport viewport, ButtonElement element) {
        return switch (element.anchorY) {
            case NONE -> element.y;
            case TOP -> element.y - (viewport.getWorldHeight() * .5f);
            case BOTTOM -> element.y + (viewport.getWorldHeight() * .5f) - (element.height + element.thickness);
            case CENTER -> element.y - (element.height * .5f);
        };
    }

    public record Style(
            Color defaultBackground,
            Color defaultOutline,
            @Nullable Color onHoverBackground,
            @Nullable Color onHoverOutline,
            @Nullable Color onPressBackground,
            @Nullable Color onPressOutline,
            int outlineThickness
    ) {
        public Color getHoverBackground() {
            return onHoverBackground != null ? onHoverBackground : defaultBackground;
        }

        public Color getHoverOutline() {
            return onHoverOutline != null ? onHoverOutline : defaultOutline;
        }

        public Color getPressBackground() {
            return onPressBackground != null ? onPressBackground : defaultBackground;
        }

        public Color getPressOutline() {
            return onPressOutline != null ? onPressOutline : defaultOutline;
        }
    }
}
