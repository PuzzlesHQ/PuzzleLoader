package com.github.puzzle.game;

import com.badlogic.gdx.graphics.Texture;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import finalforeach.cosmicreach.util.Identifier;
import org.intellij.lang.annotations.Language;

import static com.github.puzzle.core.Constants.MOD_ID;

public class ClientGlobals {

    public static final Identifier LanguageEnUs = Identifier.of(MOD_ID, "languages/en-US.json");
    public static Language SelectedLanguage;

    public static Texture whitePixel = PuzzleGameAssetLoader.LOADER.loadResourceSync(Identifier.of(MOD_ID, "textures/special/white-1x1.png"), Texture.class);
}
