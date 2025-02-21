package io.github.puzzle.cosmic.api.util;

import io.github.puzzle.cosmic.util.ApiGen;

@ApiGen("Identifier")
public interface IPuzzleIdentifier {

    String _getNamespace();
    String _getName();

    default String asString() {
        return _getNamespace() + ":" + _getName();
    }
}
