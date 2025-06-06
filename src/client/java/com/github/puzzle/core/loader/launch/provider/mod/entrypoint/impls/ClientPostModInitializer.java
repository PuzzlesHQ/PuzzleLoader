package com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls;

import com.github.puzzle.core.loader.util.PuzzleEntrypointUtil;
import com.github.puzzle.core.localization.LanguageManager;
import com.github.puzzle.core.localization.TranslationKey;

public interface ClientPostModInitializer {
    String ENTRYPOINT_KEY = "client_postInit";

    void onPostInit();

    static void invokeEntrypoint() {
        PuzzleEntrypointUtil.invoke(ENTRYPOINT_KEY, ClientPostModInitializer.class, ClientPostModInitializer::onPostInit);
    }

}
