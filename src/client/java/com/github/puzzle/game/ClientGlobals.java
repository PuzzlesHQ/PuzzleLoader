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

    public static List<IZoneRenderer> renderers = new ArrayList<>();
    public static Map<String,Integer> rendererIndexMap = new HashMap<>();
    public static int rendererIndex = 0;

    public static final Identifier LanguageEnUs = Identifier.of(MOD_ID, "languages/en-US.json");
    public static Language SelectedLanguage;

    public static Texture whitePixel = PuzzleGameAssetLoader.LOADER.loadResourceSync(Identifier.of(MOD_ID, "textures/special/white-1x1.png"), Texture.class);

    public static void initRenderers() {
        Logger LOGGER = LoggerFactory.getLogger("Puzzle | Renderers");

        Reflections ref = new Reflections();

        Set<Class<? extends IZoneRenderer>> classes = ref.getSubTypesOf(IZoneRenderer.class);
        LOGGER.warn("Getting renderers");

        for (Class<? extends IZoneRenderer> c : classes) {
            try {
                LOGGER.warn("\t{}",c.getName());
                int currrentIndex = renderers.size();
                renderers.add(c.getDeclaredConstructor().newInstance());
                rendererIndexMap.put(c.getName(),currrentIndex);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                LOGGER.warn("Can't use class \"{}\" as renderer",c.getName());
            }

        }
    }
}
