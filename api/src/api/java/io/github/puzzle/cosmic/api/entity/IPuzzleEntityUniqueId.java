package io.github.puzzle.cosmic.api.entity;

import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

@ApiGen("EntityUniqueId")
public interface IPuzzleEntityUniqueId {

    long _getTime();
    int _getRand();
    int _getNumber();

}
