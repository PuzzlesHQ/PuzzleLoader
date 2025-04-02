package com.github.puzzle.core.loader.localization;

import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Locale;
public class PuzzleLanguageStandard implements ILanguageFile {

    @ApiStatus.Internal
    private transient HashMap<String, String> internalMap = new HashMap<>();

//    @ApiStatus.Internal
//    private static transient HashMap<String, TranslationKey>

    @Override
    public boolean contains(String key) {
        return internalMap.containsKey(key);
    }

    @Override
    public String get(String key) {
        return "";
    }

    @Override
    public String get(String key, Object... obj) {
        return "";
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Locale getFallback() {
        return null;
    }
}
