package io.github.puzzle.cosmic.api.util;

import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("Identifier")
public interface IPuzzleIdentifier {

    String _getNamespace();
    String _getName();

    default String asString() {
        return _getNamespace() + ":" + _getName();
    }
}
