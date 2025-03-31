package com.github.puzzle.game.ui.surface.element;

import com.github.puzzle.game.ui.surface.SurfaceImpl;
import de.pottgames.tuningfork.SoundBuffer;
import finalforeach.cosmicreach.GameAssetLoader;

public class CRTextInputElement extends TextInputElement {

    public static final SoundBuffer onClickSound = GameAssetLoader.getSound("base:sounds/ui/e-button-click.ogg");
    public static final SoundBuffer onHoverSound = GameAssetLoader.getSound("base:sounds/ui/e-button-hover.ogg");

    public CRTextInputElement(SurfaceImpl surface){
        super(surface);
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
