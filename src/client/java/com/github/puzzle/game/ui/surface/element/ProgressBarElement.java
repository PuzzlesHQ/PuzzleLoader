package com.github.puzzle.game.ui.surface.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.puzzle.game.ui.surface.Surface;
import com.github.puzzle.core.loader.engine.GameLoader;
import com.github.puzzle.game.ClientGlobals;
import com.github.puzzle.game.engine.rendering.text.FormatText;
import com.github.puzzle.game.engine.rendering.text.FormattedTextRenderer;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public class ProgressBarElement extends AbstractElement implements GameLoader.ProgressBar {

    Color fg_full;
    Color fg_empty;
    Color bg;

    public int width = 500;
    public int height = 30;

    String text = "";
    float progress = 0;
    int max = 0;
    public float step = 0;

    public ProgressBarElement(Color fg_full, Color fg_empty, Color bg) {
        this.fg_full = fg_full;
        this.fg_empty = fg_empty;
        this.bg = bg;

        setMax(100);
    }

    @Override
    public void render(Surface surface) {
        Batch batch = surface.getBatch();

        float vx = getVisualX(surface.getViewport(), this);
        float vy = getVisualY(surface.getViewport(), this);

        tmp.set(batch.getColor());

        batch.setColor(bg);
        batch.draw(ClientGlobals.whitePixel, vx, vy, width + 4, height + 4);

        boolean full = (progress == max || max < 1);
        batch.setColor(full ? fg_full : fg_empty);
        batch.draw(ClientGlobals.whitePixel, vx + 2, vy + 2, width, height);

        if (full){
            batch.setColor(tmp);

            renderText(vx, vy, batch, surface);
            return;
        }

        batch.setColor(fg_full);
        batch.draw(ClientGlobals.whitePixel, vx + 2, vy + 2, progress * step, height);

        renderText(vx, vy, batch, surface);

        batch.setColor(tmp);
    }

    private void renderText(float vx, float vy, Batch batch, Surface surface) {
        if (!text.isEmpty()) {
            FormatText formatted = FormatText.of(text);
            batch.setColor(Color.WHITE);
            FontRenderer.getTextDimensions(surface.getViewport(), formatted.getText(), textDim);
            FormattedTextRenderer.drawText(batch, surface.getViewport(), formatted, vx + (width / 2f), vy + (height / 2f), HorizontalAnchor.CENTERED, VerticalAnchor.CENTERED);
        }
    }

    @Override
    public void setText(String s) {
        text = s;
    }

    @Override
    public void setProgress(float progress) {
        if (progress > max)
            this.progress = max;
        else
            this.progress = progress;
    }

    @Override
    public void setMax(int max) {
        this.max = max;
        this.step = (100f / (max * ((float) max / width)) * /* scale */ (max / 100f));
    }

    protected static float getVisualX(Viewport viewport, ProgressBarElement element) {
        return switch (element.anchorX) {
            case NONE -> element.x;
            case RIGHT -> element.x + (viewport.getWorldWidth() * .5f) - (element.width + 4);
            case LEFT -> element.x - (viewport.getWorldWidth() * .5f);
            case CENTER -> element.x - (element.width * .5f);
        };
    }

    protected static float getVisualY(Viewport viewport, ProgressBarElement element) {
        return switch (element.anchorY) {
            case NONE -> element.y;
            case TOP -> element.y - (viewport.getWorldHeight() * .5f);
            case BOTTOM -> element.y + (viewport.getWorldHeight() * .5f) - (element.height + 4);
            case CENTER -> element.y - (element.height * .5f);
        };
    }
}
