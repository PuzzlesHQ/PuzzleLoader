package io.github.puzzle.cosmic.api.entity;

import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("EntityUniqueId")
public interface IPuzzleEntityUniqueId {

    long _getTime();
    int _getRand();
    int _getNumber();

}
