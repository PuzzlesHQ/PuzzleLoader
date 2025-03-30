package com.github.puzzle.game.ui.surface.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.puzzle.game.ClientGlobals;
import com.github.puzzle.game.engine.rendering.text.FormatText;
import com.github.puzzle.game.engine.rendering.text.FormattedTextRenderer;
import com.github.puzzle.game.ui.surface.Surface;
import de.pottgames.tuningfork.SoundBuffer;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import org.lwjgl.glfw.GLFW;

public class CRButtonElement extends ButtonElement  {

    public static final SoundBuffer onClickSound = GameAssetLoader.getSound("base:sounds/ui/e-button-click.ogg");
    public static final SoundBuffer onHoverSound = GameAssetLoader.getSound("base:sounds/ui/e-button-hover.ogg");

    public CRButtonElement() {
        super();
    }

    public void onRelease() {
        super.onRelease();
        onClickSound.play();
    }

    public void onStartHover() {
        super.onStartHover();
        onHoverSound.play();
    }
}
