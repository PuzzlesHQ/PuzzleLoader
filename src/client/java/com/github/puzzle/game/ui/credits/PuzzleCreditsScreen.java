package com.github.puzzle.game.ui.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import com.github.puzzle.game.ui.surface.Surface;
import com.github.puzzle.game.ui.surface.SurfaceImpl;
import com.github.puzzle.game.ui.surface.element.ButtonElement;
import com.github.puzzle.game.ui.surface.element.CRButtonElement;
import com.github.puzzle.game.ui.surface.element.Element;
import com.github.puzzle.game.ui.surface.element.ImageElement;
import finalforeach.cosmicreach.gamestates.CreditsMenu;
import finalforeach.cosmicreach.gamestates.GameState;

public class PuzzleCreditsScreen extends SurfaceImpl {

    public PuzzleCreditsScreen() {
        super(null, null);
    }

    @Override
    public void init() {
        super.init();

        ImageElement crLogo = new ImageElement(PuzzleGameAssetLoader.LOADER.loadSync("base:textures/text-logo-hd.png", Texture.class));
        crLogo.scale = 0.5f;
        add(crLogo);
        new CreditsMenu().create();
        PuzzleCreditsMenu.addFile(CreditFile.fromVanilla(Gdx.files.classpath("post_build/Cosmic-Reach-Localization/CREDITS.txt").readString()));

        CRButtonElement e;
        add(e = new CRButtonElement() {
            @Override
            public void onRelease() {
                super.onRelease();

                GameState.switchToGameState(Surface.LAST_GAMESTATE.get());
            }
        });
        e.setAnchorX(Element.AnchorX.RIGHT);
        e.setAnchorY(Element.AnchorY.TOP);

        ButtonElement returnElement = new ButtonElement();
    }
}
