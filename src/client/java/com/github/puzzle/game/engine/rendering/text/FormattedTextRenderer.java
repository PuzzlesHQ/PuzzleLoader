package com.github.puzzle.game.engine.rendering.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormattedTextRenderer {

    static final Map<String, FormatText> textCache = new HashMap<>();

    public static void drawText(Batch batch, Viewport vp, String text, float xStart, float yStart) {
        drawText(batch, vp, text, xStart, yStart, false);
    }

    public static void drawText(Batch batch, Viewport uiViewport, String text, float xStart, float yStart, HorizontalAnchor hAnchor, VerticalAnchor vAnchor) {
        Color resetColor = batch.getColor().cpy();

        FormatText formatted = textCache.get(text);
        formatted = formatted == null ? FormatText.of(text, resetColor) : formatted;

        drawText(batch, uiViewport, formatted, xStart, yStart, hAnchor, vAnchor);
    }

    public static void drawText(Batch batch, Viewport vp, String text, float xStart, float yStart, boolean flip) {
        Color resetColor = batch.getColor().cpy();

        FormatText formatted = textCache.get(text);
        formatted = formatted == null ? FormatText.of(text, resetColor) : formatted;

        drawText(batch, vp, formatted, xStart, yStart, flip);
    }

    public static void drawText(Batch batch, Viewport vp, FormatText text, float xStart, float yStart) {
        drawText(batch, vp, text, xStart, yStart, false);
    }

    public static void drawText(Batch batch, Viewport uiViewport, FormatText text, float xStart, float yStart, HorizontalAnchor hAnchor, VerticalAnchor vAnchor) {
        float w = getWidth(uiViewport, text);
        float h = getHeight(uiViewport, text);
        if (hAnchor != null) {
            switch (hAnchor) {
                case LEFT_ALIGNED:
                    xStart -= uiViewport.getWorldWidth() / 2.0F;
                    break;
                case RIGHT_ALIGNED:
                    xStart = xStart + uiViewport.getWorldWidth() / 2.0F - w;
                    break;
                case CENTERED:
                default:
                    xStart -= w / 2.0F;
            }
        }

        if (vAnchor != null) {
            switch (vAnchor) {
                case TOP_ALIGNED:
                    yStart -= uiViewport.getWorldHeight() / 2.0F;
                    break;
                case BOTTOM_ALIGNED:
                    yStart = yStart + uiViewport.getWorldHeight() / 2.0F - h;
                    break;
                case CENTERED:
                default:
                    yStart -= h / 2.0F;
            }
        }

        drawText(batch, uiViewport, text, xStart, yStart);
    }

    public static void drawText(Batch batch, Viewport vp, FormatText text, float xStart, float yStart, boolean flip) {
        Color resetColor = batch.getColor().cpy();

        float totalSize = 0;
        List<FormatText.TextPart> parts = text.getParts();

        for (FormatText.TextPart part : parts) {
            batch.setColor(part.color());
            FontRenderer.drawText(batch, vp, part.text(), xStart + totalSize, yStart, flip);
            totalSize += part.getWidth(vp);
        }
        batch.setColor(resetColor);
    }

    public static float getWidth(Viewport vp, String text) {
        return getWidth(vp, FormatText.of(text));
    }

    public static float getHeight(Viewport vp, String text) {
        return getHeight(vp, FormatText.of(text));
    }

    public static float getWidth(Viewport vp, FormatText text) {
        float totalSize = 0;
        List<FormatText.TextPart> parts = text.getParts();

        for (FormatText.TextPart part : parts) {
            totalSize += part.getWidth(vp);
        }
        return totalSize;
    }

    public static float getHeight(Viewport vp, FormatText text) {
        float totalSize = 0;
        List<FormatText.TextPart> parts = text.getParts();

        for (FormatText.TextPart part : parts) {
            if (totalSize < part.getHeight(vp))
                totalSize = part.getHeight(vp);
        }
        return totalSize;
    }

}
