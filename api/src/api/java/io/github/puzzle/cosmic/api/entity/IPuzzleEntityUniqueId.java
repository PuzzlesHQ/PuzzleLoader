package io.github.puzzle.cosmic.api.entity;

import io.github.puzzle.cosmic.util.ApiDeclaration;

@ApiDeclaration(
        api = IPuzzleEntityUniqueId.class,
        impl = "EntityUniqueId"
)
public interface IPuzzleEntityUniqueId {

    long _getTime();
    int _getRand();
    int _getNumber();

}
