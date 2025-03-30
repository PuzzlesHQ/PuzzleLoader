package com.github.puzzle.game.ui.surface.element;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.puzzle.game.ui.surface.Surface;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import finalforeach.cosmicreach.util.Identifier;

public class ImageElement extends AbstractElement {

    public Texture texture;

    public float scale = 1;
    public float rotation = 0;

    public int texWidth, texHeight;

    public ImageElement(Identifier location) {
        this(PuzzleGameAssetLoader.LOADER.loadResourceSync(location, Texture.class));
    }

    public ImageElement(Texture texture) {
        setTexture(texture);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.texWidth = texture.getWidth();
        this.texHeight = texture.getHeight();
    }

    @Override
    public void render(Surface surface) {
        surface.getBatch().draw(
                texture,
                getVisualX(surface.getViewport(), this),
                getVisualY(surface.getViewport(), this),
                0, 0,
                texWidth * scale, texHeight * scale,
                scale, scale,
                rotation,
                0, 0,
                texWidth, texHeight,
                false, true
        );
    }

    protected static float getVisualX(Viewport viewport, ImageElement element) {
        float width = element.texWidth * element.scale;

        return switch (element.anchorX) {
            case NONE -> element.x;
            case RIGHT -> element.x + (viewport.getWorldWidth() * .5f) - (width + 4);
            case LEFT -> element.x - (viewport.getWorldWidth() * .5f);
            case CENTER -> element.x - width * .5f;
        };
    }

    protected static float getVisualY(Viewport viewport, ImageElement element) {
        float height = element.texHeight * element.scale;

        return switch (element.anchorY) {
            case NONE -> element.y;
            case TOP -> element.y - (viewport.getWorldHeight() * .5f);
            case BOTTOM -> element.y + (viewport.getWorldHeight() * .5f) - (height + 4);
            case CENTER -> element.y - height * .5f;
        };
    }

}
