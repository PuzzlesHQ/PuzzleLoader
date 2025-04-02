package com.github.puzzle.core.loader.localization;

import java.util.Locale;

public interface ILanguageFile {

    boolean contains(String key);
    String get(String key);
    String get(String key, Object... obj);

    Locale getLocale();
    Locale getFallback();

}
