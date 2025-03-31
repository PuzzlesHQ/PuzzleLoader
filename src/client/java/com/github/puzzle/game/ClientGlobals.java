package com.github.puzzle.game;

import com.badlogic.gdx.graphics.Texture;
import com.github.puzzle.core.Constants;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.rendering.IZoneRenderer;
import finalforeach.cosmicreach.util.Identifier;
import org.intellij.lang.annotations.Language;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.github.puzzle.core.Constants.MOD_ID;

public class ClientGlobals {

    public static final Identifier LanguageEnUs = Identifier.of(MOD_ID, "languages/en-US.json");
    public static Language SelectedLanguage;

    public static Texture whitePixel = PuzzleGameAssetLoader.LOADER.loadResourceSync(Identifier.of(MOD_ID, "textures/special/white-1x1.png"), Texture.class);
}
