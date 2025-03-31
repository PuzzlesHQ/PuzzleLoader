package com.github.puzzle.game.ui.surface.element;

import de.pottgames.tuningfork.SoundBuffer;
import finalforeach.cosmicreach.GameAssetLoader;

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
